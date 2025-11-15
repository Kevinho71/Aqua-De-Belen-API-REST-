package com.perfumeria.aquadebelen.aquadebelen.ventas.repository;

import java.util.List;

import com.perfumeria.aquadebelen.aquadebelen.ventas.model.Venta;

public interface VentaDAO {
    void store(Venta transaccion);
    Venta findById(Integer id);
    void deleteById(Integer id);
    List<Venta> findALL();

    Integer nextId ();
}
