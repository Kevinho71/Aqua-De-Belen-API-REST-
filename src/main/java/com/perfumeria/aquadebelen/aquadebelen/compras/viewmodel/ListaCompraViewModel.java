package com.perfumeria.aquadebelen.aquadebelen.compras.viewmodel;

public class ListaCompraViewModel {

    Integer id;
    String proveedor;
    String costoBruto;
    String costoNeto;
    String descuentoTotal;
    Integer loteId;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLoteId() {
        return loteId;
    }

    public void setLoteId(Integer loteId) {
        this.loteId = loteId;
    }

}
