package com.perfumeria.aquadebelen.aquadebelen.compras.repository;

import com.perfumeria.aquadebelen.aquadebelen.compras.model.DetalleCompra;

public interface DetalleCompraDAO {
    
    Integer nextId();
    
    DetalleCompra store(DetalleCompra detalleCompra);
}
