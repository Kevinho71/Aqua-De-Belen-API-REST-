package com.perfumeria.aquadebelen.aquadebelen.inventario.repository;

import java.util.List;

import com.perfumeria.aquadebelen.aquadebelen.inventario.model.PedidoSugerido;
import com.perfumeria.aquadebelen.aquadebelen.inventario.model.PedidoSugerido.EstadoPedidoSugerido;

public interface PedidoSugeridoDAO {
    
    PedidoSugerido store(PedidoSugerido pedidoSugerido);
    
    PedidoSugerido findById(Integer id);
    
    List<PedidoSugerido> findAll();
    
    List<PedidoSugerido> findByEstado(EstadoPedidoSugerido estado);
    
    List<PedidoSugerido> findByProductoId(Integer productoId);
    
    void delete(Integer id);
}
