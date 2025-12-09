package com.perfumeria.aquadebelen.aquadebelen.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatsDTO {
    
    // Estadísticas de ventas
    private Double totalVentas;
    private Long numeroVentas;
    
    // Estadísticas de compras
    private Double totalCompras;
    private Long numeroCompras;
    
    // Productos por vencer
    private Integer productosProximosVencer;
    
    // Productos bajo stock
    private Integer productosBajoStock;
    
    // Clientes registrados
    private Long clientesRegistrados;
}
