package com.perfumeria.aquadebelen.aquadebelen.ventas.viewmodel;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;


@Component
public class VentaViewModel {
    String ventaId;
    String cliente;
    String totalBruto;
    String descuentoTotal;
    String totalNeto;
    String conFactura;
    String fecha;
    List<DetalleVentaViewModel> detalles;

    /*public TransaccionViewModel(TransaccionPresenter pres){
        this.ventaId=pres.getventaId();
        this.cliente=pres.getCliente();
        this.totalBruto=pres.getTotalBruto();
        this.descuentoTotal=pres.getdescuentoTotal();
        this.totalNeto=pres.getTotalNeto();
        this.conFactura=pres.getConFactura();
        this.fecha=pres.getFecha();
    }*/

    public VentaViewModel(){};

    /*public List<TransaccionViewModel> list (List<TransaccionPresenter> listPres){
        List<TransaccionViewModel> lista = new ArrayList<>();
        for (TransaccionPresenter tp : listPres){
            TransaccionViewModel tvm = new TransaccionViewModel(tp);
            lista.add(tvm);
        }

        return lista;
    }*/

    public void add(DetalleVentaViewModel d){
        if(this.detalles == null){
            List<DetalleVentaViewModel> lista = new ArrayList<>();
            this.setDetalles(lista);
        }
        detalles.add(d);
    }

    public String getventaId() {
        return ventaId;
    }

    public String getCliente() {
        return cliente;
    }

    public String getTotalBruto() {
        return totalBruto;
    }

    public String getdescuentoTotal() {
        return descuentoTotal;
    }

    public String getTotalNeto() {
        return totalNeto;
    }

    public String getConFactura() {
        return conFactura;
    }

    public String getFecha() {
        return fecha;
    }

    public List<DetalleVentaViewModel> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleVentaViewModel> detalles) {
        this.detalles = detalles;
    }

    public void setVentaId(String ventaId) {
        this.ventaId = ventaId;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public void setTotalBruto(String totalBruto) {
        this.totalBruto = totalBruto;
    }

    public void setdescuentoTotal(String descuentoTotal) {
        this.descuentoTotal = descuentoTotal;
    }

    public void setTotalNeto(String totalNeto) {
        this.totalNeto = totalNeto;
    }

    public void setConFactura(String conFactura) {
        this.conFactura = conFactura;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    
}
