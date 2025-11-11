package com.perfumeria.aquadebelen.aquadebelen.compras.repository;

import com.perfumeria.aquadebelen.aquadebelen.compras.model.Movimiento;

public interface MovimientoDAO {
    
    void store(Movimiento movimiento);
    
    Integer nextId();
}
