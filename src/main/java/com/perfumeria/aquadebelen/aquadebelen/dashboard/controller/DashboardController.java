package com.perfumeria.aquadebelen.aquadebelen.dashboard.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perfumeria.aquadebelen.aquadebelen.dashboard.dto.*;
import com.perfumeria.aquadebelen.aquadebelen.dashboard.service.AglomeracionService;
import com.perfumeria.aquadebelen.aquadebelen.dashboard.service.DashboardService;
import com.perfumeria.aquadebelen.aquadebelen.dashboard.service.InventarioAnalisisService;
import com.perfumeria.aquadebelen.aquadebelen.dashboard.viewmodel.InventoryKPIsViewModel;
import com.perfumeria.aquadebelen.aquadebelen.inventario.service.PedidoAutomaticoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/dashboard")
@Tag(name = "Dashboard Inventario", description = "KPIs y métricas de análisis de inventario")
public class DashboardController {

    private final InventarioAnalisisService analisisService;
    private final AglomeracionService aglomeracionService;
    private final PedidoAutomaticoService pedidoAutomaticoService;
    
    @Autowired
    private DashboardService dashboardService;

    public DashboardController(InventarioAnalisisService analisisService,
                              AglomeracionService aglomeracionService,
                              PedidoAutomaticoService pedidoAutomaticoService) {
        this.analisisService = analisisService;
        this.aglomeracionService = aglomeracionService;
        this.pedidoAutomaticoService = pedidoAutomaticoService;
    }

    @Operation(summary = "Obtener KPIs de Inventario", description = "Calcula y devuelve métricas ABC, EOQ, ROP y estado de reposición. Genera automáticamente pedidos sugeridos para productos con stock <= ROP.")
    @GetMapping("/inventory-kpis")
    public ResponseEntity<List<InventoryKPIsViewModel>> getInventoryKPIs() {
        // Generar pedidos automáticos si hay alertas ROP
        pedidoAutomaticoService.generarPedidosAutomaticos();
        
        return ResponseEntity.ok(analisisService.obtenerKPIs());
    }

    @Operation(summary = "Obtener alertas de Punto de Reorden", description = "Filtra productos que necesitan reorden (stock <= ROP).")
    @GetMapping("/alertas-rop")
    public ResponseEntity<List<InventoryKPIsViewModel>> getAlertasROP() {
        // Los pedidos ya fueron generados en /inventory-kpis (se llama primero desde el frontend)
        List<InventoryKPIsViewModel> alertas = analisisService.obtenerKPIs()
            .stream()
            .filter(kpi -> "REORDENAR".equals(kpi.estadoReposicion()))
            .collect(Collectors.toList());
        return ResponseEntity.ok(alertas);
    }

    @Operation(summary = "Obtener pedidos consolidados por proveedor", 
               description = "Agrupa pedidos sugeridos por proveedor y calcula ahorro por consolidación (Principio de Aglomeración).")
    @GetMapping("/aglomeracion")
    public ResponseEntity<Map<String, Object>> getPedidosConsolidados() {
        return ResponseEntity.ok(aglomeracionService.obtenerPedidosConsolidados());
    }

    /**
     * GET /api/v1/dashboard/estadisticas
     * Obtiene todas las estadísticas generales del dashboard:
     * - Total de ventas (monto)
     * - Número de ventas
     * - Total de compras (monto)
     * - Número de compras
     * - Productos próximos a vencer
     * - Productos bajo stock
     * - Clientes registrados
     */
    @Operation(summary = "Obtener estadísticas generales", description = "Estadísticas del dashboard: ventas, compras, stock bajo, productos por vencer, clientes")
    @GetMapping("/estadisticas")
    public ResponseEntity<DashboardStatsDTO> obtenerEstadisticasGenerales() {
        DashboardStatsDTO stats = dashboardService.obtenerEstadisticasGenerales();
        return ResponseEntity.ok(stats);
    }

    /**
     * GET /api/v1/dashboard/top-productos-stock
     * Obtiene los 5 productos con mayor stock disponible
     */
    @Operation(summary = "Top 5 productos con mayor stock", description = "Retorna los 5 productos con más unidades en inventario")
    @GetMapping("/top-productos-stock")
    public ResponseEntity<List<ProductoStockDTO>> obtenerTop5ProductosConMayorStock() {
        List<ProductoStockDTO> productos = dashboardService.obtenerTop5ProductosConMayorStock();
        return ResponseEntity.ok(productos);
    }

    /**
     * GET /api/v1/dashboard/ventas-recientes
     * Obtiene las últimas 5 ventas realizadas
     */
    @Operation(summary = "Últimas 5 ventas", description = "Retorna las 5 ventas más recientes con cliente, fecha y total")
    @GetMapping("/ventas-recientes")
    public ResponseEntity<List<VentaRecienteDTO>> obtenerUltimas5Ventas() {
        List<VentaRecienteDTO> ventas = dashboardService.obtenerUltimas5Ventas();
        return ResponseEntity.ok(ventas);
    }

    /**
     * GET /api/v1/dashboard/distribucion-stock
     * Obtiene la distribución de stock por producto (para gráfico de pie)
     * Retorna los 6 productos principales con mayor stock
     */
    @Operation(summary = "Distribución de stock", description = "Distribución porcentual de stock por producto para gráfico de pie")
    @GetMapping("/distribucion-stock")
    public ResponseEntity<List<DistribucionStockDTO>> obtenerDistribucionStock() {
        List<DistribucionStockDTO> distribucion = dashboardService.obtenerDistribucionStock();
        return ResponseEntity.ok(distribucion);
    }

    /**
     * GET /api/v1/dashboard/sublotes-proximos-vencer
     * Obtiene los sublotes que vencen en los próximos 30 días
     */
    @Operation(summary = "Sublotes próximos a vencer", description = "Retorna sublotes que vencen en los próximos 30 días")
    @GetMapping("/sublotes-proximos-vencer")
    public ResponseEntity<List<SubloteProximoVencerDTO>> obtenerSublotesProximosVencer() {
        List<SubloteProximoVencerDTO> sublotes = dashboardService.obtenerSublotesProximosVencer();
        return ResponseEntity.ok(sublotes);
    }
}
