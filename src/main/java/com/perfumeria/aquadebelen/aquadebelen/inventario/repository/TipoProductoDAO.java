package com.perfumeria.aquadebelen.aquadebelen.inventario.repository;

import java.util.List;

import com.perfumeria.aquadebelen.aquadebelen.inventario.model.TipoProducto;

public interface TipoProductoDAO {
    TipoProducto findById(Integer id);
    List<TipoProducto> list();
}
