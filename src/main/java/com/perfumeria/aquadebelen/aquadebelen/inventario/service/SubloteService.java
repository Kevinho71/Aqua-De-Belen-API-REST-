package com.perfumeria.aquadebelen.aquadebelen.inventario.service;

import org.springframework.stereotype.Service;

import com.perfumeria.aquadebelen.aquadebelen.inventario.model.EstadoSublote;
import com.perfumeria.aquadebelen.aquadebelen.inventario.model.Sublote;
import com.perfumeria.aquadebelen.aquadebelen.inventario.repository.SubloteDAO;

@Service
public class SubloteService {

    private SubloteDAO sDAO;

    public SubloteService(SubloteDAO sDAO) {
        this.sDAO = sDAO;
    }

    public Sublote buscarProximoAVencer(Integer productoId) {
        return sDAO.findProximoAVencerByProductoId(productoId);
    }

    public void descontarCantidad(Sublote sublote, double cantidad) {
        double nuevaCantidad = sublote.getCantidadActual() - cantidad;
        sublote.setCantidadActual(nuevaCantidad);
        actualizarEstado(sublote);
        sDAO.store(sublote);
    }

    private void actualizarEstado(Sublote sublote) {
        if (sublote.getCantidadActual() <= 0) {
            sublote.setEstado(EstadoSublote.AGOTADO);
        } else if (sublote.getCantidadActual() < sublote.getCantidadInicial() * 0.2) {
            sublote.setEstado(EstadoSublote.POCA_CANTIDAD);
        } else {
            sublote.setEstado(EstadoSublote.DISPONIBLE);
        }
    }
}
