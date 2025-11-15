package com.perfumeria.aquadebelen.aquadebelen.compras.viewmodel;

public class ListaCompraViewModel {

    String proveedor;
    String costoBruto;
    String costoNeto;
    String descuentoTotal;
    // String loteId;

    String fecha;

    public ListaCompraViewModel() {
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

}
