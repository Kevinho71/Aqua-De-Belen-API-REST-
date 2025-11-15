package com.perfumeria.aquadebelen.aquadebelen.inventario.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perfumeria.aquadebelen.aquadebelen.compras.model.Compra;
import com.perfumeria.aquadebelen.aquadebelen.compras.model.DetalleCompra;
import com.perfumeria.aquadebelen.aquadebelen.inventario.model.EstadoSublote;
import com.perfumeria.aquadebelen.aquadebelen.inventario.model.Lote;
import com.perfumeria.aquadebelen.aquadebelen.inventario.model.Sublote;
import com.perfumeria.aquadebelen.aquadebelen.inventario.repository.LoteDAO;
import com.perfumeria.aquadebelen.aquadebelen.inventario.repository.SubloteDAO;

@Service
public class LoteService {
    
    @Autowired
    private LoteDAO lDAO;
    
    @Autowired
    private SubloteDAO sDAO;

    public Lote createLote(Compra compra) {
        Lote lote = new Lote();
        lote.setId(lDAO.nextId());
        lote.setFechaIngreso(compra.getFecha());
        // NO guardamos el lote aquí, se guardará en cascada con la Compra
        crearSublotes(lote, compra.getDetallesCompra());
        return lote;
    }

    public void crearSublotes(Lote lote, List<DetalleCompra> detalles) {
        // Obtener el próximo ID base una sola vez
        Integer nextId = sDAO.nextId();
        
        for (int i = 0; i < detalles.size(); i++) {
            DetalleCompra detalle = detalles.get(i);
            Sublote sublote = new Sublote();
            // Incrementar el ID para cada sublote
            sublote.setId(nextId + i);
            sublote.setProducto(detalle.getProducto());
            sublote.setCantidadInicial(detalle.getCantidad());
            sublote.setCantidadActual(detalle.getCantidad());
            sublote.setCostoUnitario(detalle.getCostoUnitario());
            sublote.setEstado(EstadoSublote.DISPONIBLE);
            sublote.setCodigoSublote(generarCodigoSublote(lote.getId(), detalle.getProducto().getId()));
            sublote.setFechaProduccion(LocalDate.now());
            sublote.setFechaVencimiento(detalle.getFechaVencimiento());
            
            // Establecer la relación bidireccional DetalleCompra ↔ Sublote
            sublote.setDetalleCompra(detalle);
            
            // Usar el método addSublote para establecer la relación bidireccional Lote ↔ Sublote
            lote.addSublote(sublote);
        }
    }

    private String generarCodigoSublote(Integer loteId, Integer productoId) {
        return "SL-" + loteId + "-" + productoId + "-" + System.currentTimeMillis();
    }

}
