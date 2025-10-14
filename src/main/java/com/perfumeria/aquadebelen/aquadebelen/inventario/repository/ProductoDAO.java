package com.perfumeria.aquadebelen.aquadebelen.inventario.repository;

import com.perfumeria.aquadebelen.aquadebelen.inventario.model.Producto;

public interface ProductoDAO {
    Producto findById(Integer id);
    void store(Producto producto);
}
