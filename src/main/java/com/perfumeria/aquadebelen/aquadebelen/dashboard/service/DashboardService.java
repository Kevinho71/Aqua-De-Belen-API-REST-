package com.perfumeria.aquadebelen.aquadebelen.dashboard.service;

import com.perfumeria.aquadebelen.aquadebelen.dashboard.dao.DashboardDAO;
import com.perfumeria.aquadebelen.aquadebelen.dashboard.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DashboardService {

    @Autowired
    private DashboardDAO dashboardDAO;

    /**
     * Obtiene las estadísticas generales del dashboard
     */
    @Transactional(readOnly = true)
    public DashboardStatsDTO obtenerEstadisticasGenerales() {
        return dashboardDAO.obtenerEstadisticasGenerales();
    }

    /**
     * Obtiene los 5 productos con mayor stock
     */
    @Transactional(readOnly = true)
    public List<ProductoStockDTO> obtenerTop5ProductosConMayorStock() {
        return dashboardDAO.obtenerTop5ProductosConMayorStock();
    }

    /**
     * Obtiene las últimas 5 ventas realizadas
     */
    @Transactional(readOnly = true)
    public List<VentaRecienteDTO> obtenerUltimas5Ventas() {
        return dashboardDAO.obtenerUltimas5Ventas();
    }

    /**
     * Obtiene la distribución de stock por producto
     */
    @Transactional(readOnly = true)
    public List<DistribucionStockDTO> obtenerDistribucionStock() {
        return dashboardDAO.obtenerDistribucionStock();
    }

    /**
     * Obtiene los sublotes próximos a vencer (30 días)
     */
    @Transactional(readOnly = true)
    public List<SubloteProximoVencerDTO> obtenerSublotesProximosVencer() {
        return dashboardDAO.obtenerSublotesProximosVencer();
    }
}
