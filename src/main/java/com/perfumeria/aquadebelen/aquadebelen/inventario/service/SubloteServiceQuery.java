package com.perfumeria.aquadebelen.aquadebelen.inventario.service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.perfumeria.aquadebelen.aquadebelen.inventario.DTO.SubloteDTOResponse;
import com.perfumeria.aquadebelen.aquadebelen.inventario.model.Sublote;
import com.perfumeria.aquadebelen.aquadebelen.inventario.repository.SubloteDAO;

@Service
public class SubloteServiceQuery {

    private final SubloteDAO subloteDAO;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public SubloteServiceQuery(SubloteDAO subloteDAO) {
        this.subloteDAO = subloteDAO;
    }

    public SubloteDTOResponse buscar(Integer id) {
        Sublote sublote = subloteDAO.findById(id);
        return mapToDTOResponse(sublote);
    }

    public List<SubloteDTOResponse> listar() {
        List<Sublote> sublotes = subloteDAO.list();
        List<SubloteDTOResponse> response = new ArrayList<>();
        
        for (Sublote sublote : sublotes) {
            response.add(mapToDTOResponse(sublote));
        }
        
        return response;
    }

    private SubloteDTOResponse mapToDTOResponse(Sublote sublote) {
        return new SubloteDTOResponse(
            sublote.getId(),
            sublote.getCodigoSublote(),
            sublote.getProducto() != null ? sublote.getProducto().getNombre() : "N/A",
            sublote.getFechaVencimiento() != null ? sublote.getFechaVencimiento().format(formatter) : "N/A",
            sublote.getFechaProduccion() != null ? sublote.getFechaProduccion().format(formatter) : "N/A",
            sublote.getCantidadInicial(),
            sublote.getCantidadActual(),
            sublote.getCostoUnitario(),
            sublote.getEstado() != null ? sublote.getEstado().toString() : "N/A",
            sublote.getLote() != null ? sublote.getLote().getId() : null
        );
    }
}
