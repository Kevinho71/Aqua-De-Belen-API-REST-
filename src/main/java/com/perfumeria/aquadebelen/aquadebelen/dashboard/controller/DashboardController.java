package com.perfumeria.aquadebelen.aquadebelen.dashboard.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perfumeria.aquadebelen.aquadebelen.dashboard.service.AglomeracionService;
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
}
