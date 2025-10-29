package com.perfumeria.aquadebelen.aquadebelen.compras.viewmodel;

public class DetalleCompraViewModel {

    String compraId;
    String producto;
    String tipoProducto;
    String cantidad;
    String costoUnitario;
    String descuento;
    String subtotal;
    

    public DetalleCompraViewModel() {
    }




    
    public String getCompraId() {
        return compraId;
    }
    
    public void setCompraId(String compraId) {
        this.compraId = compraId;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getCostoUnitario() {
        return costoUnitario;
    }

    public void setCostoUnitario(String costoUnitario) {
        this.costoUnitario = costoUnitario;
    }

    public String getDescuento() {
        return descuento;
    }

    public void setDescuento(String descuento) {
        this.descuento = descuento;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }





    public String getTipoProducto() {
        return tipoProducto;
    }





    public void setTipoProducto(String tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

}
