package com.perfumeria.aquadebelen.aquadebelen.inventario.service;

import java.util.List;

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
        Sublote sublote = sDAO.findProximoAVencerByProductoId(productoId);
        if (sublote == null) {
            throw new RuntimeException("No hay inventario disponible para el producto con ID: " + productoId);
        }
        return sublote;
    }

    /**
     * Descuenta cantidad de un producto usando FIFO (First In, First Out)
     * Descuenta primero de los sublotes más próximos a vencer
     */
    public void descontarCantidadPorProducto(Integer productoId, double cantidadSolicitada) {
        // Obtener stock total disponible
        Double stockTotal = sDAO.sumCantidadActualByProductoId(productoId);
        
        if (stockTotal < cantidadSolicitada) {
            throw new RuntimeException("Stock insuficiente para el producto. Disponible: " 
                + stockTotal + ", Solicitado: " + cantidadSolicitada);
        }

        // Obtener sublotes disponibles ordenados por fecha de vencimiento (FIFO)
        List<Sublote> sublotesDisponibles = sDAO.findByProductoId(productoId);
        
        double cantidadRestante = cantidadSolicitada;
        
        for (Sublote sublote : sublotesDisponibles) {
            if (cantidadRestante <= 0) {
                break;
            }
            
            // Saltar sublotes agotados
            if (sublote.getCantidadActual() <= 0) {
                continue;
            }
            
            // Determinar cuánto descontar de este sublote
            double cantidadADescontar = Math.min(sublote.getCantidadActual(), cantidadRestante);
            
            // Descontar del sublote
            sublote.setCantidadActual(sublote.getCantidadActual() - cantidadADescontar);
            actualizarEstado(sublote);
            sDAO.store(sublote);
            
            // Reducir la cantidad restante
            cantidadRestante -= cantidadADescontar;
        }
        
        if (cantidadRestante > 0) {
            throw new RuntimeException("No se pudo completar el descuento. Faltaron: " + cantidadRestante);
        }
    }

    public void descontarCantidad(Sublote sublote, double cantidad) {
        if (sublote.getCantidadActual() < cantidad) {
            throw new RuntimeException("Cantidad insuficiente en inventario. Disponible: " 
                + sublote.getCantidadActual() + ", Solicitado: " + cantidad);
        }
        double nuevaCantidad = sublote.getCantidadActual() - cantidad;
        sublote.setCantidadActual(nuevaCantidad);
        actualizarEstado(sublote);
        sDAO.store(sublote);
    }

    private void actualizarEstado(Sublote sublote) {
        if (sublote.getCantidadActual() <= 0) {
            sublote.setEstado(EstadoSublote.AGOTADO);
        } else if (sublote.getCantidadActual() < sublote.getCantidadInicial() * 0.2) {
            sublote.setEstado(EstadoSublote.POCO_CANTIDAD);
        } else {
            sublote.setEstado(EstadoSublote.DISPONIBLE);
        }
    }
}
