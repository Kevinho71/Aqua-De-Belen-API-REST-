package com.perfumeria.aquadebelen.aquadebelen.ventas.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.perfumeria.aquadebelen.aquadebelen.clientes.repository.ClienteDAO;
import com.perfumeria.aquadebelen.aquadebelen.inventario.model.Producto;
import com.perfumeria.aquadebelen.aquadebelen.inventario.repository.ProductoDAO;
import com.perfumeria.aquadebelen.aquadebelen.ventas.DTO.DetalleVentaRequest;
import com.perfumeria.aquadebelen.aquadebelen.ventas.DTO.DetalleVentaResponse;
import com.perfumeria.aquadebelen.aquadebelen.ventas.DTO.VentaRequest;
import com.perfumeria.aquadebelen.aquadebelen.ventas.DTO.VentaResponse;
import com.perfumeria.aquadebelen.aquadebelen.ventas.model.DetalleVenta;
import com.perfumeria.aquadebelen.aquadebelen.ventas.model.Venta;
import com.perfumeria.aquadebelen.aquadebelen.ventas.repository.MetodoDePagoDAO;
import com.perfumeria.aquadebelen.aquadebelen.ventas.repository.VentaDAO;

@Service
public class VentaService {
    private final VentaDAO tDAO;
    private final MetodoDePagoDAO mpDAO;
    private final ClienteDAO cDAO;
    private final ProductoDAO pDAO;

    public VentaService(VentaDAO tDAO, MetodoDePagoDAO mpDAO, ClienteDAO cDAO, ProductoDAO pDAO) {
        this.tDAO = tDAO;
        this.mpDAO = mpDAO;
        this.cDAO = cDAO;
        this.pDAO = pDAO;

    }

    public VentaResponse store(Integer id, VentaRequest req) {
        Venta venta = new Venta();
        if (id == null) {
            venta.setId(tDAO.nextId());
            venta.setCliente(cDAO.findById(req.clienteId()));
            venta.setFecha(LocalDateTime.now());
            venta.setTotalBruto(calcularTotalBruto(req.detalles()));
            venta.setMetodoDePago(mpDAO.findById(req.metodoDePagoId()));
            venta.setConFactura(req.conFactura());
            agregarDetalles(req.detalles(), venta);
            venta.setTotalNeto(venta.getTotalBruto() - venta.getDescuentoTotal());
            tDAO.store(venta);
        } else {
            venta = tDAO.findById(id);
            venta.setCliente(cDAO.findById(req.clienteId()));
            venta.setMetodoDePago(mpDAO.findById(req.metodoDePagoId()));
            venta.setTotalBruto(calcularTotalBruto(req.detalles()));
            venta.setConFactura(req.conFactura());
            actualizarDetalles(req.detalles(), venta);
            venta.setTotalNeto(venta.getTotalBruto() - venta.getDescuentoTotal());
            tDAO.store(venta);
        }
        Venta Ventas2 = tDAO.findById(venta.getId());
        return mapToDtoResponse(Ventas2);
    }

    public double calcularTotalBruto(List<DetalleVentaRequest> detalles) {
        double totalBruto = 0;
        for (DetalleVentaRequest dt : detalles) {

            // AQUI CREAMOS UN NUEVO PRODUCTO CON EL FIN DE HALLAR SU PRECIO Y VAMOS
            // CALCULANDO EL TOTAL BRUTO
            Producto producto = pDAO.findById(dt.productoId());
            double subtotal = producto.getPrecio() * dt.cantidad();
            totalBruto = totalBruto + subtotal;
        }
        return totalBruto;
    }

    public void agregarDetalles(List<DetalleVentaRequest> detalles, Venta venta) {

        double descuentoTotal=0;
        for (DetalleVentaRequest dt : detalles) {
            DetalleVenta detalle = new DetalleVenta();
            Producto producto = pDAO.findById(dt.productoId());
            detalle.setCantidad(dt.cantidad());
            detalle.setProducto(producto);
            detalle.setDescuento(dt.descuento());
            double subtotal = (producto.getPrecio() * dt.cantidad())-dt.descuento();
            detalle.setSubtotal(subtotal);
            descuentoTotal=descuentoTotal+dt.descuento();
            venta.addDetalle(detalle);
        }
        venta.setDescuentoTotal(descuentoTotal);
    }

    public void actualizarDetalles(List<DetalleVentaRequest> detalles, Venta venta) {
        venta.getDetallesVentas().clear();
        agregarDetalles(detalles, venta);

    }

    // BORRAR UNA Ventas
    public void borrar(Integer id) {
        tDAO.deleteById(id);
    }

    // BUSCAR UNA Ventas
    public VentaResponse buscar(Integer id) {
        Venta venta = tDAO.findById(id);
        return mapToDtoResponse(venta);
    }

    public List<VentaResponse> listar() {
        List<Venta> lista = tDAO.findALL();
        List<VentaResponse> listaResp = new ArrayList<>();
        for (Venta t : lista) {
            VentaResponse e = mapToDtoResponse(t);
            listaResp.add(e);
        }
        return listaResp;
    }

    public VentaResponse mapToDtoResponse(Venta venta) {
        List<DetalleVentaResponse> listResp = new ArrayList<>();
        for (DetalleVenta dt : venta.getDetallesVentas()) {
            DetalleVentaResponse dtr = new DetalleVentaResponse(dt.getVenta().getId(), dt.getId(),
                    dt.getProducto().getNombre(), dt.getProducto().getPrecio(), dt.getCantidad(), dt.getDescuento(), dt.getSubtotal());
            listResp.add(dtr);
        }
        return new VentaResponse(venta.getId(),
                venta.getCliente().getNombre() + " " + venta.getCliente().getApellido(),
                venta.getTotalBruto(), venta.getDescuentoTotal(), venta.getTotalNeto(),
                venta.isConFactura(), venta.getFecha(), listResp);
    }

}
