/*package com.perfumeria.aquadebelen.aquadebelen.compras.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.perfumeria.aquadebelen.aquadebelen.compras.DTO.CompraDTORequest;
import com.perfumeria.aquadebelen.aquadebelen.compras.DTO.CompraDTOResponse;
import com.perfumeria.aquadebelen.aquadebelen.compras.DTO.DetalleCompraDTORequest;
import com.perfumeria.aquadebelen.aquadebelen.compras.model.Compra;
import com.perfumeria.aquadebelen.aquadebelen.compras.model.DetalleCompra;
import com.perfumeria.aquadebelen.aquadebelen.compras.repository.ComprasDAO;
import com.perfumeria.aquadebelen.aquadebelen.inventario.model.Producto;
import com.perfumeria.aquadebelen.aquadebelen.inventario.repository.ProveedorDAO;
import com.perfumeria.aquadebelen.aquadebelen.ventas.DTO.DetalleCompraDTORequest;
import com.perfumeria.aquadebelen.aquadebelen.ventas.DTO.DetalleVentaResponse;
import com.perfumeria.aquadebelen.aquadebelen.ventas.DTO.CompraDTOResponse;
import com.perfumeria.aquadebelen.aquadebelen.ventas.DTO.CompraDTOResponse;
import com.perfumeria.aquadebelen.aquadebelen.ventas.model.DetalleCompra;
import com.perfumeria.aquadebelen.aquadebelen.ventas.model.Compra;

@Service
public class CompraService {

    private ComprasDAO cDAO;
    private ProveedorDAO pDAO;

    public CompraService(ComprasDAO cDAO, ProveedorDAO pDAO) {
        this.cDAO = cDAO;
        this.pDAO = pDAO;
    }



    
    public CompraDTOResponse store(Integer id, CompraDTORequest req) {
        Compra compra = new Compra();
        if (id == null) {
            compra.setId(cDAO.nextId());
            compra.setProveedor(pDAO.findById(req.proveedorId()));
            compra.setFecha(LocalDateTime.now());
            compra.setCostoBruto(calcularCostoBruto(req.detalles()));
            compra.setMetodoDePago(mpDAO.findById(req.metodoDePagoId()));
            compra.setConFactura(req.conFactura());
            agregarDetalles(req.detalles(), compra);
            compra.setTotalNeto(compra.getTotalBruto() - compra.getDescuentoTotal());
            cDAO.store(compra);
        } else {
            compra = cDAO.findById(id);
            compra.setCliente(cDAO.findById(req.clienteId()));
            compra.setMetodoDePago(mpDAO.findById(req.metodoDePagoId()));
            compra.setTotalBruto(calcularCostoBruto(req.detalles()));
            compra.setConFactura(req.conFactura());
            actualizarDetalles(req.detalles(), compra);
            compra.setTotalNeto(compra.getTotalBruto() - compra.getDescuentoTotal());
            cDAO.store(compra);
        }
        Compra Ventas2 = cDAO.findById(compra.getId());
        return mapToDtoResponse(Ventas2);
    }

    public double calcularCostoBruto(List<DetalleCompraDTORequest> detalles) {
        double costoBruto = 0;
        for (DetalleCompraDTORequest dt : detalles) {

            // AQUI CREAMOS UN NUEVO PRODUCTO CON EL FIN DE HALLAR SU PRECIO Y VAMOS
            // CALCULANDO EL TOTAL BRUTO
            Producto producto = cDAO.findById(dt.productoId());
            double subtotal = producto.getPrecio() * dt.cantidad();
            costoBruto = costoBruto + subtotal;
        }
        return costoBruto;
    }

    public void agregarDetalles(List<DetalleCompraDTORequest> detalles, Compra compra) {

        double descuentoTotal=0;
        for (DetalleCompraDTORequest dt : detalles) {
            DetalleCompra detalle = new DetalleCompra();
            Producto producto = cDAO.findById(dt.productoId());
            detalle.setCantidad(dt.cantidad());
            detalle.setProducto(producto);
            detalle.setDescuento(dt.descuento());
            double subtotal = (producto.getPrecio() * dt.cantidad())-dt.descuento();
            detalle.setSubtotal(subtotal);
            descuentoTotal=descuentoTotal+dt.descuento();
            compra.addDetalle(detalle);
        }
        compra.setDescuentoTotal(descuentoTotal);
    }

    public void actualizarDetalles(List<DetalleCompraDTORequest> detalles, Compra compra) {
        compra.getDetallesVentas().clear();
        agregarDetalles(detalles, compra);

    }

    // BORRAR UNA Ventas
    public void borrar(Integer id) {
        cDAO.deleteById(id);
    }

    // BUSCAR UNA Ventas
    public CompraDTOResponse buscar(Integer id) {
        Compra compra = cDAO.findById(id);
        return mapToDtoResponse(compra);
    }

    public List<CompraDTOResponse> listar() {
        List<Compra> lista = cDAO.findALL();
        List<CompraDTOResponse> listaResp = new ArrayList<>();
        for (Compra t : lista) {
            CompraDTOResponse e = mapToDtoResponse(t);
            listaResp.add(e);
        }
        return listaResp;
    }

    public CompraDTOResponse mapToDtoResponse(Compra compra) {
        List<DetalleVentaResponse> listResp = new ArrayList<>();
        for (DetalleCompra dt : compra.getDetallesVentas()) {
            DetalleVentaResponse dtr = new DetalleVentaResponse(dt.getVenta().getId(), dt.getId(),
                    dt.getProducto().getNombre(), dt.getProducto().getPrecio(), dt.getCantidad(), dt.getDescuento(), dt.getSubtotal());
            listResp.add(dtr);
        }
        return new CompraDTOResponse(compra.getId(),
                compra.getCliente().getNombre() + " " + compra.getCliente().getApellido(),
                compra.getTotalBruto(), compra.getDescuentoTotal(), compra.getTotalNeto(),
                compra.isConFactura(), compra.getFecha(), listResp);
    }

}
*/