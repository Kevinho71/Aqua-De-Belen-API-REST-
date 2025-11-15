package com.perfumeria.aquadebelen.aquadebelen.compras.repository;

import java.util.List;

import com.perfumeria.aquadebelen.aquadebelen.compras.model.Movimiento;

public interface MovimientoDAO {
    
    void store(Movimiento movimiento);
    
    Integer nextId();
    
    Movimiento findById(Integer id);
    
    List<Movimiento> findAll();
}
