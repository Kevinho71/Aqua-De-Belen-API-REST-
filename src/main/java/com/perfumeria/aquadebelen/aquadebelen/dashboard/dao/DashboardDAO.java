package com.perfumeria.aquadebelen.aquadebelen.dashboard.dao;

import com.perfumeria.aquadebelen.aquadebelen.dashboard.dto.*;
import java.util.List;

public interface DashboardDAO {
    
    /**
     * Obtiene estadísticas generales del dashboard
     */
    DashboardStatsDTO obtenerEstadisticasGenerales();
    
    /**
     * Obtiene los 5 productos con mayor stock
     */
    List<ProductoStockDTO> obtenerTop5ProductosConMayorStock();
    
    /**
     * Obtiene las últimas 5 ventas realizadas
     */
    List<VentaRecienteDTO> obtenerUltimas5Ventas();
    
    /**
     * Obtiene la distribución de stock por producto (para gráfico de pie)
     */
    List<DistribucionStockDTO> obtenerDistribucionStock();
    
    /**
     * Obtiene los sublotes próximos a vencer (30 días)
     */
    List<SubloteProximoVencerDTO> obtenerSublotesProximosVencer();
}
