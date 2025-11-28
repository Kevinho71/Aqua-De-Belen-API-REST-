package com.perfumeria.aquadebelen.aquadebelen.ventas.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.perfumeria.aquadebelen.aquadebelen.ventas.model.Venta;

public interface VentaDAO {
    void store(Venta transaccion);
    Venta findById(Integer id);
    void deleteById(Integer id);
    List<Venta> findALL();
    List<Venta> findALL(int page, int size);

    Integer nextId ();
    
    List<Venta> findByFilters(Integer clienteId, LocalDateTime fechaInicio, LocalDateTime fechaFin);

    List<Object[]> sumVentasPorProductoUltimoAnio();
}
