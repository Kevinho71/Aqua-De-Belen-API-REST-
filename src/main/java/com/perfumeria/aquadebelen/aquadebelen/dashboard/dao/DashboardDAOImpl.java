package com.perfumeria.aquadebelen.aquadebelen.dashboard.dao;

import com.perfumeria.aquadebelen.aquadebelen.dashboard.dto.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DashboardDAOImpl implements DashboardDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public DashboardStatsDTO obtenerEstadisticasGenerales() {
        DashboardStatsDTO stats = new DashboardStatsDTO();

        // Total de ventas (monto) - usa total_neto
        Query queryTotalVentas = entityManager.createNativeQuery(
            "SELECT COALESCE(SUM(total_neto), 0) FROM venta"
        );
        Double totalVentas = ((Number) queryTotalVentas.getSingleResult()).doubleValue();
        stats.setTotalVentas(totalVentas);

        // Número de ventas
        Query queryNumVentas = entityManager.createNativeQuery(
            "SELECT COUNT(*) FROM venta"
        );
        Long numeroVentas = ((Number) queryNumVentas.getSingleResult()).longValue();
        stats.setNumeroVentas(numeroVentas);

        // Total de compras (monto) - usa costo_neto
        Query queryTotalCompras = entityManager.createNativeQuery(
            "SELECT COALESCE(SUM(costo_neto), 0) FROM compra"
        );
        Double totalCompras = ((Number) queryTotalCompras.getSingleResult()).doubleValue();
        stats.setTotalCompras(totalCompras);

        // Número de compras
        Query queryNumCompras = entityManager.createNativeQuery(
            "SELECT COUNT(*) FROM compra"
        );
        Long numeroCompras = ((Number) queryNumCompras.getSingleResult()).longValue();
        stats.setNumeroCompras(numeroCompras);

        // Productos próximos a vencer (30 días)
        LocalDate fechaLimite = LocalDate.now().plusDays(30);
        Query queryProximosVencer = entityManager.createNativeQuery(
            "SELECT COUNT(DISTINCT producto_id) FROM sublote " +
            "WHERE fecha_vencimiento <= :fechaLimite " +
            "AND fecha_vencimiento >= CURRENT_DATE " +
            "AND estado IN ('DISPONIBLE', 'POCO_CANTIDAD')"
        );
        queryProximosVencer.setParameter("fechaLimite", fechaLimite);
        Integer productosProximosVencer = ((Number) queryProximosVencer.getSingleResult()).intValue();
        stats.setProductosProximosVencer(productosProximosVencer);

        // Productos bajo stock (stock actual < punto de reorden)
        Query queryBajoStock = entityManager.createNativeQuery(
            "SELECT COUNT(*) FROM ( " +
            "  SELECT p.id " +
            "  FROM producto p " +
            "  LEFT JOIN sublote s ON p.id = s.producto_id AND s.estado IN ('DISPONIBLE', 'POCO_CANTIDAD') " +
            "  GROUP BY p.id, p.punto_reorden " +
            "  HAVING COALESCE(SUM(s.cantidad_actual), 0) < COALESCE(p.punto_reorden, 10) " +
            ") AS bajo_stock"
        );
        Integer productosBajoStock = ((Number) queryBajoStock.getSingleResult()).intValue();
        stats.setProductosBajoStock(productosBajoStock);

        // Clientes registrados
        Query queryClientes = entityManager.createNativeQuery(
            "SELECT COUNT(*) FROM cliente"
        );
        Long clientesRegistrados = ((Number) queryClientes.getSingleResult()).longValue();
        stats.setClientesRegistrados(clientesRegistrados);

        return stats;
    }

    @Override
    public List<ProductoStockDTO> obtenerTop5ProductosConMayorStock() {
        Query query = entityManager.createNativeQuery(
            "SELECT p.id, p.nombre, COALESCE(SUM(s.cantidad_actual), 0) as stock_total " +
            "FROM producto p " +
            "LEFT JOIN sublote s ON p.id = s.producto_id AND s.estado IN ('DISPONIBLE', 'POCO_CANTIDAD') " +
            "WHERE p.descontinuado = false OR p.descontinuado IS NULL " +
            "GROUP BY p.id, p.nombre " +
            "ORDER BY stock_total DESC " +
            "LIMIT 5"
        );

        List<Object[]> results = query.getResultList();
        List<ProductoStockDTO> productos = new ArrayList<>();

        for (Object[] row : results) {
            ProductoStockDTO dto = new ProductoStockDTO();
            dto.setProductoId((Integer) row[0]);
            dto.setNombreProducto((String) row[1]);
            dto.setStockTotal(((Number) row[2]).doubleValue());
            productos.add(dto);
        }

        return productos;
    }

    @Override
    public List<VentaRecienteDTO> obtenerUltimas5Ventas() {
        Query query = entityManager.createNativeQuery(
            "SELECT v.id, c.nombre, v.fecha, v.total_neto " +
            "FROM venta v " +
            "JOIN cliente c ON v.cliente_id = c.id " +
            "ORDER BY v.fecha DESC " +
            "LIMIT 5"
        );

        List<Object[]> results = query.getResultList();
        List<VentaRecienteDTO> ventas = new ArrayList<>();

        for (Object[] row : results) {
            VentaRecienteDTO dto = new VentaRecienteDTO();
            dto.setVentaId((Integer) row[0]);
            dto.setNombreCliente((String) row[1]);
            dto.setFechaVenta(((java.sql.Timestamp) row[2]).toLocalDateTime());
            dto.setTotalVenta(((Number) row[3]).doubleValue());
            ventas.add(dto);
        }

        return ventas;
    }

    @Override
    public List<DistribucionStockDTO> obtenerDistribucionStock() {
        // Primero obtenemos el stock total general
        Query queryStockTotal = entityManager.createNativeQuery(
            "SELECT COALESCE(SUM(s.cantidad_actual), 0) " +
            "FROM sublote s " +
            "WHERE s.estado IN ('DISPONIBLE', 'POCO_CANTIDAD')"
        );
        Double stockTotalGeneral = ((Number) queryStockTotal.getSingleResult()).doubleValue();

        // Luego obtenemos el stock por producto para calcular porcentajes
        Query query = entityManager.createNativeQuery(
            "SELECT p.nombre, COALESCE(SUM(s.cantidad_actual), 0) as stock_total " +
            "FROM producto p " +
            "LEFT JOIN sublote s ON p.id = s.producto_id AND s.estado IN ('DISPONIBLE', 'POCO_CANTIDAD') " +
            "WHERE (p.descontinuado = false OR p.descontinuado IS NULL) " +
            "GROUP BY p.id, p.nombre " +
            "HAVING COALESCE(SUM(s.cantidad_actual), 0) > 0 " +
            "ORDER BY stock_total DESC " +
            "LIMIT 6"
        );

        List<Object[]> results = query.getResultList();
        List<DistribucionStockDTO> distribucion = new ArrayList<>();

        for (Object[] row : results) {
            DistribucionStockDTO dto = new DistribucionStockDTO();
            dto.setNombreProducto((String) row[0]);
            Double stockProducto = ((Number) row[1]).doubleValue();
            dto.setStockTotal(stockProducto);
            
            // Calcular porcentaje
            Double porcentaje = stockTotalGeneral > 0 
                ? (stockProducto / stockTotalGeneral) * 100 
                : 0.0;
            dto.setPorcentaje(Math.round(porcentaje * 100.0) / 100.0); // 2 decimales
            
            distribucion.add(dto);
        }

        return distribucion;
    }

    @Override
    public List<SubloteProximoVencerDTO> obtenerSublotesProximosVencer() {
        LocalDate fechaLimite = LocalDate.now().plusDays(30);
        
        Query query = entityManager.createNativeQuery(
            "SELECT p.nombre, s.cantidad_actual, s.fecha_vencimiento " +
            "FROM sublote s " +
            "JOIN producto p ON s.producto_id = p.id " +
            "WHERE s.fecha_vencimiento <= :fechaLimite " +
            "AND s.fecha_vencimiento >= CURRENT_DATE " +
            "AND s.estado IN ('DISPONIBLE', 'POCO_CANTIDAD') " +
            "AND s.cantidad_actual > 0 " +
            "ORDER BY s.fecha_vencimiento ASC " +
            "LIMIT 10"
        );
        query.setParameter("fechaLimite", fechaLimite);

        List<Object[]> results = query.getResultList();
        List<SubloteProximoVencerDTO> sublotes = new ArrayList<>();

        for (Object[] row : results) {
            SubloteProximoVencerDTO dto = new SubloteProximoVencerDTO();
            dto.setNombreProducto((String) row[0]);
            dto.setCantidad(((Number) row[1]).doubleValue());
            dto.setFechaVencimiento(((java.sql.Date) row[2]).toLocalDate());
            sublotes.add(dto);
        }

        return sublotes;
    }
}
