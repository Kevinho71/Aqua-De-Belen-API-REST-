package com.perfumeria.aquadebelen.aquadebelen.compras.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.perfumeria.aquadebelen.aquadebelen.compras.model.DetalleCompra;
import com.perfumeria.aquadebelen.aquadebelen.compras.model.Movimiento;
import com.perfumeria.aquadebelen.aquadebelen.compras.repository.MovimientoDAO;
import com.perfumeria.aquadebelen.aquadebelen.ventas.model.DetalleVenta;

@Service
public class MovimientoService {

    private MovimientoDAO mDAO;

    public MovimientoService(MovimientoDAO mDAO) {
        this.mDAO = mDAO;
    }

    public void crearMovimientoCompra(DetalleCompra detalleCompra) {
        Movimiento movimiento = new Movimiento();
        movimiento.setId(mDAO.nextId());
        movimiento.setCantidad(detalleCompra.getCantidad());
        movimiento.setCostoUnitario(detalleCompra.getCostoUnitario());
        movimiento.setCostoTotal(detalleCompra.getSubtotal());
        movimiento.setPrecioUnitario(0);
        movimiento.setPrecioTotal(0);
        movimiento.setFecha(LocalDateTime.now());
        movimiento.setReferenciaTipo("COMPRA");
        movimiento.setDetalleCompraId(detalleCompra);
        movimiento.setReferenciaId(detalleCompra.getCompra().getId());
        mDAO.store(movimiento);
    }

    public void crearMovimientoVenta(DetalleVenta detalleVenta, double precioUnitario) {
        Movimiento movimiento = new Movimiento();
        movimiento.setId(mDAO.nextId());
        movimiento.setCantidad(detalleVenta.getCantidad());
        movimiento.setPrecioUnitario(precioUnitario);
        movimiento.setPrecioTotal(detalleVenta.getSubtotal());
        movimiento.setCostoUnitario(0);
        movimiento.setCostoTotal(0);
        movimiento.setFecha(LocalDateTime.now());
        movimiento.setReferenciaTipo("VENTA");
        movimiento.setDetalleVentaId(detalleVenta);
        movimiento.setReferenciaId(detalleVenta.getVenta().getId());
        mDAO.store(movimiento);
    }
}
