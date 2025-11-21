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
    private Integer idRaw;
    private Integer loteIdRaw;
    private Double cantidadInicialRaw;
    private Double cantidadActualRaw;
    private Double costoUnitarioRaw;

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

    public Integer getIdRaw() {
        return idRaw;
    }

    public void setIdRaw(Integer idRaw) {
        this.idRaw = idRaw;
    }

    public Integer getLoteIdRaw() {
        return loteIdRaw;
    }

    public void setLoteIdRaw(Integer loteIdRaw) {
        this.loteIdRaw = loteIdRaw;
    }

    public Double getCantidadInicialRaw() {
        return cantidadInicialRaw;
    }

    public void setCantidadInicialRaw(Double cantidadInicialRaw) {
        this.cantidadInicialRaw = cantidadInicialRaw;
    }

    public Double getCantidadActualRaw() {
        return cantidadActualRaw;
    }

    public void setCantidadActualRaw(Double cantidadActualRaw) {
        this.cantidadActualRaw = cantidadActualRaw;
    }

    public Double getCostoUnitarioRaw() {
        return costoUnitarioRaw;
    }

    public void setCostoUnitarioRaw(Double costoUnitarioRaw) {
        this.costoUnitarioRaw = costoUnitarioRaw;
    }
}
