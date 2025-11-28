package com.perfumeria.aquadebelen.aquadebelen.inventario.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.perfumeria.aquadebelen.aquadebelen.inventario.DTO.LoteDTOResponse;
import com.perfumeria.aquadebelen.aquadebelen.inventario.model.Lote;
import com.perfumeria.aquadebelen.aquadebelen.inventario.repository.LoteDAO;

@Service
public class LoteServiceQuery {

    private final LoteDAO loteDAO;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public LoteServiceQuery(LoteDAO loteDAO) {
        this.loteDAO = loteDAO;
    }

    public LoteDTOResponse buscar(Integer id) {
        Lote lote = loteDAO.findById(id);
        return mapToDTOResponse(lote);
    }

    public List<LoteDTOResponse> listar() {
        List<Lote> lotes = loteDAO.list();
        List<LoteDTOResponse> response = new ArrayList<>();
        
        for (Lote lote : lotes) {
            response.add(mapToDTOResponse(lote));
        }
        
        return response;
    }

    public List<LoteDTOResponse> listar(int page, int size) {
        List<Lote> lotes = loteDAO.list(page, size);
        List<LoteDTOResponse> response = new ArrayList<>();
        
        for (Lote lote : lotes) {
            response.add(mapToDTOResponse(lote));
        }
        
        return response;
    }

    public List<LoteDTOResponse> buscarPorFiltros(Integer compraId, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        List<Lote> lotes = loteDAO.findByFilters(compraId, fechaInicio, fechaFin);
        List<LoteDTOResponse> response = new ArrayList<>();
        for (Lote lote : lotes) {
            response.add(mapToDTOResponse(lote));
        }
        return response;
    }

    private LoteDTOResponse mapToDTOResponse(Lote lote) {
        return new LoteDTOResponse(
            lote.getId(),
            lote.getFechaIngreso() != null ? lote.getFechaIngreso().format(formatter) : "N/A",
            lote.getCompra() != null ? lote.getCompra().getId() : null,
            lote.getSublotes() != null ? lote.getSublotes().size() : 0
        );
    }
}
