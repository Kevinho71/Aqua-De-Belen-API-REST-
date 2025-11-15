package com.perfumeria.aquadebelen.aquadebelen.compras.repository;

import java.util.List;

import com.perfumeria.aquadebelen.aquadebelen.compras.model.Compra;

public interface ComprasDAO {

    void store(Compra compra);
    void deleteById(Integer id);
    Compra findById(Integer id);
    List<Compra> findAll();
    Integer nextId();
}
