package com.perfumeria.aquadebelen.aquadebelen.dashboard.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.perfumeria.aquadebelen.aquadebelen.dashboard.viewmodel.InventoryKPIsViewModel;
import com.perfumeria.aquadebelen.aquadebelen.inventario.model.Producto;
import com.perfumeria.aquadebelen.aquadebelen.inventario.repository.ProductoDAO;
import com.perfumeria.aquadebelen.aquadebelen.inventario.repository.SubloteDAO;
import com.perfumeria.aquadebelen.aquadebelen.ventas.model.DetalleVenta;
import com.perfumeria.aquadebelen.aquadebelen.ventas.repository.VentaDAO;

@Service
public class InventarioAnalisisService {

    private final ProductoDAO productoDAO;
    private final SubloteDAO subloteDAO;
    private final VentaDAO ventaDAO; // Assuming we can access sales data

    public InventarioAnalisisService(ProductoDAO productoDAO, SubloteDAO subloteDAO, VentaDAO ventaDAO) {
        this.productoDAO = productoDAO;
        this.subloteDAO = subloteDAO;
        this.ventaDAO = ventaDAO;
    }

    public List<InventoryKPIsViewModel> calcularMetricasInventario() {
        List<Producto> productos = productoDAO.list();
        
        // 1. Calcular Demanda Anual y Valor de Consumo para ABC
        Map<Integer, Double> ventasPorProducto = calcularVentasAnuales(productos);
        
        // 2. Clasificación ABC (solo en memoria, sin guardar)
        Map<Integer, String> clasificacionABC = calcularClasificacionABC(productos, ventasPorProducto);
        
        List<InventoryKPIsViewModel> kpis = new ArrayList<>();
        
        // 3. Calcular EOQ y ROP dinámicamente
        for (Producto p : productos) {
            double demandaAnual = ventasPorProducto.getOrDefault(p.getId(), 0.0);
            
            // EOQ = sqrt(2 * D * S / H)
            int eoq = 0;
            if (p.getCostoAlmacenamiento() != null && p.getCostoAlmacenamiento() > 0 && p.getCostoPedido() != null) {
                double eoqCalc = Math.sqrt((2 * demandaAnual * p.getCostoPedido()) / p.getCostoAlmacenamiento());
                eoq = (int) Math.ceil(eoqCalc);
            }

            // ROP = (Demanda Diaria * Tiempo Entrega) + Stock Seguridad
            int rop = 0;
            if (p.getTiempoEntrega() != null && p.getStockSeguridad() != null) {
                double demandaDiaria = demandaAnual / 365.0;
                double ropCalc = (demandaDiaria * p.getTiempoEntrega()) + p.getStockSeguridad();
                rop = (int) Math.ceil(ropCalc);
            }
            
            Double stockActual = subloteDAO.sumCantidadActualByProductoId(p.getId());
            if (stockActual == null) stockActual = 0.0;

            String estado = "OK";
            if (rop > 0 && stockActual <= rop) {
                estado = "REORDENAR";
            }

            kpis.add(new InventoryKPIsViewModel(
                p.getId(),
                p.getNombre(),
                clasificacionABC.getOrDefault(p.getId(), "C"),
                eoq,
                rop,
                stockActual,
                estado
            ));
        }
        
        return kpis;
    }

    private Map<Integer, Double> calcularVentasAnuales(List<Producto> productos) {
        List<Object[]> resultados = ventaDAO.sumVentasPorProductoUltimoAnio();
        return resultados.stream()
            .collect(Collectors.toMap(
                row -> ((Number) row[0]).intValue(),
                row -> ((Number) row[1]).doubleValue()
            ));
    }

    private Map<Integer, String> calcularClasificacionABC(List<Producto> productos, Map<Integer, Double> ventas) {
        Map<Integer, String> clasificaciones = new java.util.HashMap<>();
        
        // Ordenar por valor de consumo (cantidad * precio actual)
        List<Producto> ordenados = productos.stream()
            .sorted(Comparator.comparingDouble((Producto p) -> {
                double cantidad = ventas.getOrDefault(p.getId(), 0.0);
                return cantidad; 
            }).reversed())
            .collect(Collectors.toList());

        double ventaTotal = ordenados.stream()
            .mapToDouble(p -> ventas.getOrDefault(p.getId(), 0.0))
            .sum();

        double acumulado = 0;
        for (Producto p : ordenados) {
            double venta = ventas.getOrDefault(p.getId(), 0.0);
            acumulado += venta;
            double porcentaje = (ventaTotal > 0) ? (acumulado / ventaTotal) : 0;

            String clasificacion;
            if (porcentaje <= 0.80) {
                clasificacion = "A";
            } else if (porcentaje <= 0.95) {
                clasificacion = "B";
            } else {
                clasificacion = "C";
            }
            
            clasificaciones.put(p.getId(), clasificacion);
        }
        
        return clasificaciones;
    }

    public List<InventoryKPIsViewModel> obtenerKPIs() {
        // Cálculo 100% dinámico, sin guardar en BD
        return calcularMetricasInventario();
    }
}
