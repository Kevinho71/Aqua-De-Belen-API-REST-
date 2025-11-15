package com.perfumeria.aquadebelen.aquadebelen.ventas.presenter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.perfumeria.aquadebelen.aquadebelen.ventas.DTO.DetalleVentaResponse;
import com.perfumeria.aquadebelen.aquadebelen.ventas.DTO.VentaResponse;
import com.perfumeria.aquadebelen.aquadebelen.ventas.viewmodel.DetalleVentaViewModel;
import com.perfumeria.aquadebelen.aquadebelen.ventas.viewmodel.ListVentaViewModel;
import com.perfumeria.aquadebelen.aquadebelen.ventas.viewmodel.VentaViewModel;

@Component
public class VentaPresenter {

    String ventaId;
    String cliente;
    String totalBruto;
    String descuento;
    String totalNeto;
    String conFactura;
    String fecha;

    public VentaViewModel present(VentaResponse resp) {
        VentaViewModel tvm = new VentaViewModel();
        tvm.setVentaId(String.valueOf(resp.ventaId()));
        tvm.setCliente(resp.cliente().toString());
        tvm.setTotalBruto(String.valueOf(resp.totalBruto()) + " Bs");
        tvm.setTotalNeto(String.valueOf(resp.totalNeto()) + " Bs");
        tvm.setdescuentoTotal(String.valueOf(resp.descuentoTotal()) + " Bs");

        tvm.setConFactura(formatFactura(resp.conFactura()));
        tvm.setFecha(resp.fecha());
        tvm.setDetalles(presentDetalles(resp.detalles()));

        return tvm;
    }

    public List<DetalleVentaViewModel> presentDetalles(List<DetalleVentaResponse> listResp) {
        List<DetalleVentaViewModel> dtvm = new ArrayList<>();
        for (DetalleVentaResponse dtr : listResp) {
            DetalleVentaViewModel d = new DetalleVentaViewModel();
            d.setIdDetalle(String.valueOf(dtr.detalleId()));
            d.setProducto(dtr.producto());
            d.setCostoUnitario(String.valueOf(dtr.costoUnitario()) + " Bs");
            d.setCantidad(String.valueOf(dtr.cantidad()) + " u");
            d.setDescuento(String.valueOf(dtr.descuento()) + " Bs");
            d.setSubtotal(String.valueOf(dtr.subtotal()) + " Bs");

            dtvm.add(d);
        }

        return dtvm;
    }

    public List<ListVentaViewModel> presentList(List<VentaResponse> listaResp) {
        List<ListVentaViewModel> lista = new ArrayList<>();
        for (VentaResponse resp : listaResp) {
            ListVentaViewModel listPres = new ListVentaViewModel();
            listPres.setVentaId(String.valueOf(resp.ventaId()));
            listPres.setCliente(resp.cliente());
            listPres.setTotalNeto(String.valueOf(resp.totalNeto()) + " Bs");
            listPres.setConFactura(formatFactura(resp.conFactura()));
            listPres.setFecha(resp.fecha());
            lista.add(listPres);

        }
        return lista;
    }

    public String formatFactura(Boolean factura) {
        String formattedFactura;
        if (factura == true) {
            formattedFactura = "Con factura";
        } else {
            formattedFactura = "Sin factura";
        }

        return formattedFactura;
    }

    // ====================================================================================================================
    // CONSTRUCTORES, GETTERS Y SETTERS

    public VentaPresenter(String ventaId, String cliente, String totalBruto, String descuento,
            String totalNeto, String conFactura, String fecha) {
        this.ventaId = ventaId;
        this.cliente = cliente;
        this.totalBruto = totalBruto;
        this.descuento = descuento;
        this.totalNeto = totalNeto;
        this.conFactura = conFactura;
        this.fecha = fecha;
    }

    public VentaPresenter() {

    }

    public String getVentaId() {
        return ventaId;
    }

    public void setVentaId(String ventaId) {
        this.ventaId = ventaId;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getTotalBruto() {
        return totalBruto;
    }

    public void setTotalBruto(String totalBruto) {
        this.totalBruto = totalBruto;
    }

    public String getDescuento() {
        return descuento;
    }

    public void setDescuento(String descuento) {
        this.descuento = descuento;
    }

    public String getTotalNeto() {
        return totalNeto;
    }

    public void setTotalNeto(String totalNeto) {
        this.totalNeto = totalNeto;
    }

    public String getConFactura() {
        return conFactura;
    }

    public void setConFactura(String conFactura) {
        this.conFactura = conFactura;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

}
