package com.perfumeria.aquadebelen.aquadebelen.compras.repository;

import java.util.List;

import com.perfumeria.aquadebelen.aquadebelen.compras.model.PrecioHistorico;

public interface PrecioHistoricoDAO {

    void save(PrecioHistorico precioHistorico);
    PrecioHistorico findUltimoPrecioByProductoId(Integer id);
    Integer nextId();
    List<PrecioHistorico> findByProductoId(Integer productoId);
    List<PrecioHistorico> findAll();
}
