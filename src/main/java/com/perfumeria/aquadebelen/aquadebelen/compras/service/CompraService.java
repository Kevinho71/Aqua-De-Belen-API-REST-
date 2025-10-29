package com.perfumeria.aquadebelen.aquadebelen.compras.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.perfumeria.aquadebelen.aquadebelen.compras.DTO.CompraDTORequest;
import com.perfumeria.aquadebelen.aquadebelen.compras.DTO.CompraDTOResponse;
import com.perfumeria.aquadebelen.aquadebelen.compras.DTO.DetalleCompraDTORequest;
import com.perfumeria.aquadebelen.aquadebelen.compras.DTO.DetalleCompraDTOResponse;
import com.perfumeria.aquadebelen.aquadebelen.compras.model.Compra;
import com.perfumeria.aquadebelen.aquadebelen.compras.model.DetalleCompra;
import com.perfumeria.aquadebelen.aquadebelen.compras.repository.ComprasDAO;
import com.perfumeria.aquadebelen.aquadebelen.inventario.model.Producto;
import com.perfumeria.aquadebelen.aquadebelen.inventario.repository.ProductoDAO;
import com.perfumeria.aquadebelen.aquadebelen.inventario.repository.ProveedorDAO;
import com.perfumeria.aquadebelen.aquadebelen.inventario.service.LoteService;

@Service
public class CompraService {

    private ComprasDAO cDAO;
    private ProveedorDAO pDAO;
    private ProductoDAO prDAO;
    private LoteService lServ;

    public CompraService(ComprasDAO cDAO, ProveedorDAO pDAO, ProductoDAO prDAO,
            LoteService lServ) {
        this.cDAO = cDAO;
        this.pDAO = pDAO;
        this.prDAO = prDAO;
        this.lServ = lServ;
    }

    public CompraDTOResponse store(Integer id, CompraDTORequest req) {
        Compra compra = new Compra();
        if (id == null) {
            compra.setId(cDAO.nextId());
            compra.setProveedor(pDAO.findById(req.proveedorId()));
            compra.setFecha(LocalDateTime.now());
            compra.setCostoBruto(calcularCostoBruto(req.detalles()));
            agregarDetalles(req.detalles(), compra);
            compra.setCostoNeto(compra.getCostoBruto() - compra.getDescuentoTotal());

            // crear lote para la compra
            //lServ.createLote(compra);
            cDAO.store(compra);
        } else {
            compra = cDAO.findById(id);
            compra.setProveedor(pDAO.findById(req.proveedorId()));
            compra.setCostoBruto(calcularCostoBruto(req.detalles()));
            actualizarDetalles(req.detalles(), compra);
            compra.setCostoNeto(compra.getCostoBruto() - compra.getDescuentoTotal());
            cDAO.store(compra);
        }
        Compra Ventas2 = cDAO.findById(compra.getId());
        return mapToDtoResponse(Ventas2);
    }

    public double calcularCostoBruto(List<DetalleCompraDTORequest> detalles) {
        double costoBruto = 0;
        for (DetalleCompraDTORequest dt : detalles) {
            double subtotal = dt.costoUnitario()*dt.cantidad();
            costoBruto = costoBruto + subtotal;
        }
        return costoBruto;
    }

    public void agregarDetalles(List<DetalleCompraDTORequest> detalles, Compra compra) {

        double descuentoTotal = 0;
        for (DetalleCompraDTORequest dt : detalles) {
            DetalleCompra detalle = new DetalleCompra();
            Producto producto = prDAO.findById(dt.productoId());
            detalle.setCantidad(dt.cantidad());
            detalle.setCostoUnitario(dt.costoUnitario());
            detalle.setDescuento(dt.descuento());
            detalle.setProducto(producto);
            double subtotal = (dt.costoUnitario() * dt.cantidad()) - dt.descuento();
            detalle.setSubtotal(subtotal);
            descuentoTotal = descuentoTotal + dt.descuento();
            compra.addDetalle(detalle);
            
        }
        compra.setDescuentoTotal(descuentoTotal);
    }

    public void actualizarDetalles(List<DetalleCompraDTORequest> detalles, Compra compra) {
        compra.getDetallesCompra().clear();
        agregarDetalles(detalles, compra);

    }

    public void borrar(Integer id) {
        cDAO.deleteById(id);
    }

    public CompraDTOResponse buscar(Integer id) {
        Compra compra = cDAO.findById(id);
        return mapToDtoResponse(compra);
    }

    public List<CompraDTOResponse> listar() {
        List<Compra> lista = cDAO.findAll();
        List<CompraDTOResponse> listaResp = new ArrayList<>();
        for (Compra t : lista) {
            CompraDTOResponse e = mapToDtoResponse(t);
            listaResp.add(e);
        }
        return listaResp;
    }

    public CompraDTOResponse mapToDtoResponse(Compra compra) {
        List<DetalleCompraDTOResponse> listResp = new ArrayList<>();
        for (DetalleCompra dt : compra.getDetallesCompra()) {
            DetalleCompraDTOResponse dtr = new DetalleCompraDTOResponse(dt.getCompra().getId(),
                    dt.getProducto().getNombre(), dt.getProducto().getTipoProducto().getNombre(),  dt.getCantidad(), dt.getCostoUnitario(), dt.getDescuento(),
                    dt.getSubtotal());
            listResp.add(dtr);
        }
        return new CompraDTOResponse(compra.getId(),
                compra.getCostoBruto(), compra.getCostoNeto(), compra.getDescuentoTotal(),
                compra.getProveedor().getNombre(), compra.getFecha(), listResp);
    }

}
