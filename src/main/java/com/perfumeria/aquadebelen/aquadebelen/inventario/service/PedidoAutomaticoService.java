package com.perfumeria.aquadebelen.aquadebelen.inventario.service;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.perfumeria.aquadebelen.aquadebelen.dashboard.service.InventarioAnalisisService;
import com.perfumeria.aquadebelen.aquadebelen.dashboard.viewmodel.InventoryKPIsViewModel;
import com.perfumeria.aquadebelen.aquadebelen.inventario.model.PedidoSugerido;
import com.perfumeria.aquadebelen.aquadebelen.inventario.model.PedidoSugerido.EstadoPedidoSugerido;
import com.perfumeria.aquadebelen.aquadebelen.inventario.model.Producto;
import com.perfumeria.aquadebelen.aquadebelen.inventario.repository.PedidoSugeridoDAO;
import com.perfumeria.aquadebelen.aquadebelen.inventario.repository.ProductoDAO;

@Service
public class PedidoAutomaticoService {

    private static final Logger logger = LoggerFactory.getLogger(PedidoAutomaticoService.class);

    private final InventarioAnalisisService analisisService;
    private final PedidoSugeridoDAO pedidoSugeridoDAO;
    private final ProductoDAO productoDAO;

    public PedidoAutomaticoService(InventarioAnalisisService analisisService, 
                                    PedidoSugeridoDAO pedidoSugeridoDAO,
                                    ProductoDAO productoDAO) {
        this.analisisService = analisisService;
        this.pedidoSugeridoDAO = pedidoSugeridoDAO;
        this.productoDAO = productoDAO;
    }

    /**
     * Detecta productos con stock <= ROP y genera pedidos sugeridos automáticamente
     * Se ejecuta bajo demanda cuando se consultan los KPIs
     * Sincronizado para evitar duplicados en llamadas concurrentes
     */
    @Transactional
    public synchronized int generarPedidosAutomaticos() {
        logger.debug("Verificando productos que necesitan reorden...");
        
        try {
            // Obtener KPIs con estado REORDENAR
            List<InventoryKPIsViewModel> alertas = analisisService.obtenerKPIs()
                .stream()
                .filter(kpi -> "REORDENAR".equals(kpi.estadoReposicion()))
                .toList();
            
            if (alertas.isEmpty()) {
                logger.debug("No hay productos que necesiten reorden");
                return 0;
            }
            
            logger.info("Se detectaron {} productos que necesitan reorden", alertas.size());
            
            int pedidosCreados = 0;
            
            for (InventoryKPIsViewModel kpi : alertas) {
                // Verificar si ya existe un pedido sugerido PENDIENTE para este producto
                List<PedidoSugerido> pedidosPendientes = pedidoSugeridoDAO.findByProductoId(kpi.productoId())
                    .stream()
                    .filter(p -> p.getEstado() == EstadoPedidoSugerido.PENDIENTE)
                    .toList();
                
                if (pedidosPendientes.isEmpty()) {
                    // Crear nuevo pedido sugerido
                    Producto producto = productoDAO.findById(kpi.productoId());
                    
                    if (producto != null) {
                        PedidoSugerido pedido = new PedidoSugerido(
                            producto,
                            kpi.eoq(), // Cantidad sugerida = EOQ
                            LocalDate.now(),
                            EstadoPedidoSugerido.PENDIENTE,
                            kpi.stockActual(),
                            kpi.puntoReorden()
                        );
                        
                        pedido.setObservacion(
                            String.format("Pedido automático generado. Stock actual: %.2f | ROP: %d | Clasificación: %s",
                                kpi.stockActual(), kpi.puntoReorden(), kpi.clasificacionABC())
                        );
                        
                        pedidoSugeridoDAO.store(pedido);
                        pedidosCreados++;
                        
                        logger.info("Pedido sugerido creado para producto: {} (ID: {}) - Cantidad: {}", 
                            producto.getNombre(), producto.getId(), kpi.eoq());
                    }
                } else {
                    logger.debug("Ya existe pedido pendiente para producto ID: {}", kpi.productoId());
                }
            }
            
            if (pedidosCreados > 0) {
                logger.info("Generación automática completada. Pedidos creados: {}", pedidosCreados);
            }
            
            return pedidosCreados;
            
        } catch (Exception e) {
            logger.error("Error al generar pedidos automáticos: {}", e.getMessage(), e);
            return 0;
        }
    }
}
