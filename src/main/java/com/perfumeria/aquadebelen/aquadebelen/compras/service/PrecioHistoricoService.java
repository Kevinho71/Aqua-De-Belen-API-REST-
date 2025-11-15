package com.perfumeria.aquadebelen.aquadebelen.compras.service;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

import com.perfumeria.aquadebelen.aquadebelen.compras.DTO.PrecioHistoricoResponse;
import com.perfumeria.aquadebelen.aquadebelen.compras.model.PrecioHistorico;
import com.perfumeria.aquadebelen.aquadebelen.compras.repository.PrecioHistoricoDAO;

@Service
public class PrecioHistoricoService {

    private final PrecioHistoricoDAO phDAO;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public PrecioHistoricoService(PrecioHistoricoDAO phDAO) {
        this.phDAO = phDAO;
    }

    public List<PrecioHistoricoResponse> listarPorProducto(Integer productoId) {
        List<PrecioHistorico> lista = phDAO.findByProductoId(productoId);
        return lista.stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<PrecioHistoricoResponse> listarTodos() {
        List<PrecioHistorico> lista = phDAO.findAll();
        return lista.stream()
                .map(this::mapToResponse)
                .toList();
    }

    public PrecioHistoricoResponse obtenerPrecioActual(Integer productoId) {
        PrecioHistorico precio = phDAO.findUltimoPrecioByProductoId(productoId);
        return mapToResponse(precio);
    }

    private PrecioHistoricoResponse mapToResponse(PrecioHistorico ph) {
        return new PrecioHistoricoResponse(
                ph.getId(),
                ph.getProducto().getId(),
                ph.getProducto().getNombre(),
                ph.getPrecioVenta(),
                ph.getFecha().format(FORMATTER)
        );
    }
}
