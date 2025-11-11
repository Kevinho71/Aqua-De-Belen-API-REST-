package com.perfumeria.aquadebelen.aquadebelen.inventario.viewmodel;

public class ListSubloteViewModel {
    private String codigoSublote;
    private String producto;
    private String cantidadActual;
    private String estado;
    private String fechaVencimiento;

    public ListSubloteViewModel() {
    }

    public String getCodigoSublote() {
        return codigoSublote;
    }

    public void setCodigoSublote(String codigoSublote) {
        this.codigoSublote = codigoSublote;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getCantidadActual() {
        return cantidadActual;
    }

    public void setCantidadActual(String cantidadActual) {
        this.cantidadActual = cantidadActual;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }
}
