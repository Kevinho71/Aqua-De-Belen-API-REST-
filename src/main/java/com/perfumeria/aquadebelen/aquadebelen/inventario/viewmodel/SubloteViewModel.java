package com.perfumeria.aquadebelen.aquadebelen.inventario.viewmodel;

public class SubloteViewModel {
    private String id;
    private String codigoSublote;
    private String producto;
    private String fechaVencimiento;
    private String fechaProduccion;
    private String cantidadInicial;
    private String cantidadActual;
    private String costoUnitario;
    private String estado;
    private String loteId;

    public SubloteViewModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getFechaProduccion() {
        return fechaProduccion;
    }

    public void setFechaProduccion(String fechaProduccion) {
        this.fechaProduccion = fechaProduccion;
    }

    public String getCantidadInicial() {
        return cantidadInicial;
    }

    public void setCantidadInicial(String cantidadInicial) {
        this.cantidadInicial = cantidadInicial;
    }

    public String getCantidadActual() {
        return cantidadActual;
    }

    public void setCantidadActual(String cantidadActual) {
        this.cantidadActual = cantidadActual;
    }

    public String getCostoUnitario() {
        return costoUnitario;
    }

    public void setCostoUnitario(String costoUnitario) {
        this.costoUnitario = costoUnitario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getLoteId() {
        return loteId;
    }

    public void setLoteId(String loteId) {
        this.loteId = loteId;
    }
}
