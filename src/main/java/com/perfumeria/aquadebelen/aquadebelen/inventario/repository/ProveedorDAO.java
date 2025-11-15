package com.perfumeria.aquadebelen.aquadebelen.inventario.repository;

import java.util.List;
import com.perfumeria.aquadebelen.aquadebelen.inventario.model.Proveedor;

public interface ProveedorDAO {
    void store(Proveedor proveedor);

    Proveedor findById(Integer id);

    void deleteById(Integer id);

    List<Proveedor> list();

    Integer nextId();

}
