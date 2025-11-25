package com.perfumeria.aquadebelen.aquadebelen.inventario.viewmodel;

public class ListSubloteViewModel {
    private Integer id;
    private String codigoSublote;
    private String producto;
    private String cantidadActual;
    private String estado;
    private String fechaVencimiento;
    private Integer loteId;

    public ListSubloteViewModel() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getLoteId() {
        return loteId;
    }

    public void setLoteId(Integer loteId) {
        this.loteId = loteId;
    }
}
