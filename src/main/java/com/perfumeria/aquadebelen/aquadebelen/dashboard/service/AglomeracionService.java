package com.perfumeria.aquadebelen.aquadebelen.dashboard.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.perfumeria.aquadebelen.aquadebelen.dashboard.viewmodel.AhorroConsolidacionDTO;
import com.perfumeria.aquadebelen.aquadebelen.dashboard.viewmodel.InventoryKPIsViewModel;
import com.perfumeria.aquadebelen.aquadebelen.dashboard.viewmodel.PedidoConsolidadoDTO;
import com.perfumeria.aquadebelen.aquadebelen.inventario.model.PedidoSugerido;
import com.perfumeria.aquadebelen.aquadebelen.inventario.model.PedidoSugerido.EstadoPedidoSugerido;
import com.perfumeria.aquadebelen.aquadebelen.inventario.model.Producto;
import com.perfumeria.aquadebelen.aquadebelen.inventario.model.Proveedor;
import com.perfumeria.aquadebelen.aquadebelen.inventario.repository.PedidoSugeridoDAO;
import com.perfumeria.aquadebelen.aquadebelen.inventario.repository.ProductoDAO;
import com.perfumeria.aquadebelen.aquadebelen.inventario.repository.ProveedorDAO;
import com.perfumeria.aquadebelen.aquadebelen.compras.repository.ComprasDAO;

@Service
public class AglomeracionService {

    private final InventarioAnalisisService analisisService;
    private final PedidoSugeridoDAO pedidoSugeridoDAO;
    private final ProductoDAO productoDAO;
    private final ComprasDAO compraDAO;
    private final ProveedorDAO proveedorDAO;

    public AglomeracionService(InventarioAnalisisService analisisService,
                              PedidoSugeridoDAO pedidoSugeridoDAO,
                              ProductoDAO productoDAO,
                              ComprasDAO compraDAO,
                              ProveedorDAO proveedorDAO) {
        this.analisisService = analisisService;
        this.pedidoSugeridoDAO = pedidoSugeridoDAO;
        this.productoDAO = productoDAO;
        this.compraDAO = compraDAO;
        this.proveedorDAO = proveedorDAO;
    }

    /**
     * Agrupa pedidos sugeridos pendientes por proveedor
     * y calcula el ahorro por consolidación
     */
    public Map<String, Object> obtenerPedidosConsolidados() {
        // 1. Obtener todos los pedidos sugeridos PENDIENTES
        List<PedidoSugerido> pedidosPendientes = pedidoSugeridoDAO.findByEstado(EstadoPedidoSugerido.PENDIENTE);
        
        // 2. Agrupar por proveedor (basado en última compra del producto)
        Map<Integer, List<PedidoSugerido>> porProveedor = agruparPorProveedor(pedidosPendientes);
        
        // 3. Generar DTOs con información consolidada
        List<PedidoConsolidadoDTO> consolidados = new ArrayList<>();
        double ahorroTotal = 0.0;
        
        for (Map.Entry<Integer, List<PedidoSugerido>> entry : porProveedor.entrySet()) {
            Integer proveedorId = entry.getKey();
            List<PedidoSugerido> pedidos = entry.getValue();
            
            if (proveedorId == null || pedidos.isEmpty()) {
                continue;
            }
            
            // Obtener proveedor
            Proveedor proveedor = obtenerProveedor(proveedorId);
            if (proveedor == null) {
                continue;
            }
            
            // Calcular ahorro para este proveedor
            AhorroConsolidacionDTO ahorro = calcularAhorroPorProveedor(pedidos);
            ahorroTotal += ahorro.ahorro();
            
            // Crear DTO
            List<Map<String, Object>> productosInfo = pedidos.stream()
                .map(p -> Map.of(
                    "productoId", (Object) p.getProducto().getId(),
                    "productoNombre", p.getProducto().getNombre(),
                    "cantidadSugerida", p.getCantidadSugerida(),
                    "stockActual", p.getStockActualMomento() != null ? p.getStockActualMomento() : 0.0,
                    "rop", p.getRopMomento() != null ? p.getRopMomento() : 0
                ))
                .collect(Collectors.toList());
            
            consolidados.add(new PedidoConsolidadoDTO(
                proveedorId,
                proveedor.getNombre(),
                pedidos.size(),
                productosInfo,
                ahorro
            ));
        }
        
        // 4. Retornar resultado completo
        Map<String, Object> resultado = new HashMap<>();
        resultado.put("consolidados", consolidados);
        resultado.put("ahorroTotalEstimado", ahorroTotal);
        resultado.put("totalProveedores", consolidados.size());
        resultado.put("totalPedidosPendientes", pedidosPendientes.size());
        
        return resultado;
    }

    /**
     * Calcula el ahorro de consolidar varios pedidos en uno solo
     */
    private AhorroConsolidacionDTO calcularAhorroPorProveedor(List<PedidoSugerido> pedidos) {
        int N = pedidos.size();
        
        // Obtener costo de pedido promedio de los productos
        double costoPedidoPromedio = pedidos.stream()
            .map(p -> p.getProducto().getCostoPedido())
            .filter(c -> c != null && c > 0)
            .mapToDouble(Double::doubleValue)
            .average()
            .orElse(500.0); // Default si no hay datos
        
        // Escenario 1: N pedidos separados
        double costoSeparado = N * costoPedidoPromedio;
        
        // Escenario 2: 1 pedido consolidado
        double costoConsolidado = 1 * costoPedidoPromedio;
        
        // Ahorro
        double ahorro = costoSeparado - costoConsolidado;
        double porcentajeAhorro = (ahorro / costoSeparado) * 100;
        
        return new AhorroConsolidacionDTO(
            costoSeparado,
            costoConsolidado,
            ahorro,
            porcentajeAhorro
        );
    }

    /**
     * Agrupa pedidos por proveedor basándose en la última compra del producto
     */
    private Map<Integer, List<PedidoSugerido>> agruparPorProveedor(List<PedidoSugerido> pedidos) {
        Map<Integer, List<PedidoSugerido>> mapa = new HashMap<>();
        
        for (PedidoSugerido pedido : pedidos) {
            Integer proveedorId = obtenerProveedorIdPorProducto(pedido.getProducto().getId());
            
            if (proveedorId != null) {
                mapa.computeIfAbsent(proveedorId, k -> new ArrayList<>()).add(pedido);
            }
        }
        
        return mapa;
    }

    /**
     * Obtiene el ID del proveedor basándose en la última compra del producto
     */
    private Integer obtenerProveedorIdPorProducto(Integer productoId) {
        try {
            // Buscar última compra de este producto
            List<Object[]> ultimaCompra = compraDAO.findUltimaCompraPorProducto(productoId);
            
            if (ultimaCompra != null && !ultimaCompra.isEmpty()) {
                Object[] row = ultimaCompra.get(0);
                if (row.length > 0 && row[0] != null) {
                    return ((Number) row[0]).intValue();
                }
            }
        } catch (Exception e) {
            // Si falla, retornar null
        }
        
        return null;
    }

    /**
     * Obtiene el proveedor por ID
     */
    private Proveedor obtenerProveedor(Integer proveedorId) {
        return proveedorDAO.findById(proveedorId);
    }
}
