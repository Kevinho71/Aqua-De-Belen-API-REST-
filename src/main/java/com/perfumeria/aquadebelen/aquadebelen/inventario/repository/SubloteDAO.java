package com.perfumeria.aquadebelen.aquadebelen.inventario.repository;

import java.util.List;

import com.perfumeria.aquadebelen.aquadebelen.inventario.model.Sublote;

public interface SubloteDAO {

    Sublote findById(Integer id);

    void store(Sublote producto);

    List<Sublote> list();

    Integer nextId();
    
    Sublote findProximoAVencerByProductoId(Integer productoId);
    
    List<Sublote> findDisponibles();
    
    List<Sublote> findProximosAVencer(Integer dias);

    Double sumCantidadActualByProductoId(Integer productoId);

    List<Object[]> sumCantidadActualGroupByProducto();
}
