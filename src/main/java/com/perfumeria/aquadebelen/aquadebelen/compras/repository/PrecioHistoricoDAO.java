package com.perfumeria.aquadebelen.aquadebelen.compras.repository;

import com.perfumeria.aquadebelen.aquadebelen.compras.model.PrecioHistorico;

public interface PrecioHistoricoDAO {

    void save(PrecioHistorico precioHistorico);
    PrecioHistorico findUltimoPrecioByProductoId(Integer id);
}
