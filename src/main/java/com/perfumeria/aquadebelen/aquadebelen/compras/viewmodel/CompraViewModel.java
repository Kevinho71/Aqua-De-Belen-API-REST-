package com.perfumeria.aquadebelen.aquadebelen.compras.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class CompraViewModel {

    String id;
    String costoBruto;
    String costoNeto;
    String descuentoTotal;
    //String loteId;
    String proveedor;
    String fecha;
    List<DetalleCompraViewModel> detalles;


    public CompraViewModel(){

    }


    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getCostoBruto() {
        return costoBruto;
    }
    public void setCostoBruto(String costoBruto) {
        this.costoBruto = costoBruto;
    }
    public String getCostoNeto() {
        return costoNeto;
    }
    public void setCostoNeto(String costoNeto) {
        this.costoNeto = costoNeto;
    }
    public String getDescuentoTotal() {
        return descuentoTotal;
    }
    public void setDescuentoTotal(String descuentoTotal) {
        this.descuentoTotal = descuentoTotal;
    }

    public String getProveedor() {
        return proveedor;
    }
    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }
    public List<DetalleCompraViewModel> getDetalles() {
        return detalles;
    }
    public void setDetalles(List<DetalleCompraViewModel> detalles) {
        this.detalles = detalles;
    }



    public String getFecha() {
        return fecha;
    }



    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void addDetalle(DetalleCompraViewModel detalle){
        if(this.detalles==null){
            detalles = new ArrayList<>();
        }
        detalles.add(detalle);
    }
}
