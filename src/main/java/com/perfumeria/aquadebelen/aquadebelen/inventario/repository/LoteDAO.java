package com.perfumeria.aquadebelen.aquadebelen.inventario.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.perfumeria.aquadebelen.aquadebelen.inventario.model.Lote;

public interface LoteDAO {

    Lote findById(Integer id);
    void store(Lote producto);
    List<Lote> list();
    Integer nextId();

    List<Lote> findByFilters(Integer compraId, LocalDateTime fechaInicio, LocalDateTime fechaFin);

}
