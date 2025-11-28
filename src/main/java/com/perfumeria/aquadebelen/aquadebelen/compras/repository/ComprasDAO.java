package com.perfumeria.aquadebelen.aquadebelen.compras.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.perfumeria.aquadebelen.aquadebelen.compras.model.Compra;

public interface ComprasDAO {

    void store(Compra compra);
    void deleteById(Integer id);
    Compra findById(Integer id);
    List<Compra> findAll();
    List<Compra> findAll(int page, int size);
    Integer nextId();
    
    List<Compra> findByFilters(Integer proveedorId, LocalDateTime fechaInicio, LocalDateTime fechaFin);
    
    List<Object[]> findUltimaCompraPorProducto(Integer productoId);
}
