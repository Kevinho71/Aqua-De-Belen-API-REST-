package com.perfumeria.aquadebelen.aquadebelen.inventario.repository;

import java.util.List;

import com.perfumeria.aquadebelen.aquadebelen.inventario.model.Producto;

public interface ProductoDAO {
    Producto findById(Integer id);
    void store(Producto producto);
    List<Producto> list();
    Integer nextId();
}
