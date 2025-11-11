package com.perfumeria.aquadebelen.aquadebelen.inventario.repository;

import java.util.List;

import com.perfumeria.aquadebelen.aquadebelen.inventario.model.Lote;

public interface LoteDAO {

    Lote findById(Integer id);
    void store(Lote producto);
    List<Lote> list();
    Integer nextId();

}
