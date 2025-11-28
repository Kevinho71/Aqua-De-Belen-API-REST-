package com.perfumeria.aquadebelen.aquadebelen.compras.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.perfumeria.aquadebelen.aquadebelen.compras.DTO.MovimientoDTOResponse;
import com.perfumeria.aquadebelen.aquadebelen.compras.model.Movimiento;
import com.perfumeria.aquadebelen.aquadebelen.compras.repository.MovimientoDAO;

@Service
public class MovimientoServiceQuery {

    private final MovimientoDAO movimientoDAO;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public MovimientoServiceQuery(MovimientoDAO movimientoDAO) {
        this.movimientoDAO = movimientoDAO;
    }

    public MovimientoDTOResponse buscar(Integer id) {
        Movimiento movimiento = movimientoDAO.findById(id);
        return mapToDTOResponse(movimiento);
    }

    public List<MovimientoDTOResponse> listar() {
        List<Movimiento> movimientos = movimientoDAO.findAll();
        List<MovimientoDTOResponse> response = new ArrayList<>();
        
        for (Movimiento movimiento : movimientos) {
            response.add(mapToDTOResponse(movimiento));
        }
        
        return response;
    }

    public List<MovimientoDTOResponse> buscarPorFiltros(String tipo, LocalDateTime fechaInicio, LocalDateTime fechaFin, Integer subloteId) {
        List<Movimiento> movimientos = movimientoDAO.findByFilters(tipo, fechaInicio, fechaFin, subloteId);
        List<MovimientoDTOResponse> response = new ArrayList<>();
        for (Movimiento movimiento : movimientos) {
            response.add(mapToDTOResponse(movimiento));
        }
        return response;
    }

    private MovimientoDTOResponse mapToDTOResponse(Movimiento movimiento) {
        return new MovimientoDTOResponse(
            movimiento.getId(),
            movimiento.getCantidad(),
            movimiento.getCostoUnitario(),
            movimiento.getPrecioUnitario(),
            movimiento.getCostoTotal(),
            movimiento.getPrecioTotal(),
            movimiento.getFecha() != null ? movimiento.getFecha().format(formatter) : "N/A",
            movimiento.getReferenciaTipo(),
            movimiento.getReferenciaId(),
            movimiento.getDetalleCompraId() != null ? movimiento.getDetalleCompraId().getId() : null,
            movimiento.getDetalleVentaId() != null ? movimiento.getDetalleVentaId().getId() : null
        );
    }
}
