package com.perfumeria.aquadebelen.aquadebelen.inventario.viewmodel;

public class LoteViewModel {
    private String id;
    private String fechaIngreso;
    private String compraId;
    private String cantidadSublotes;
    private Integer idRaw;
    private Integer compraIdRaw;
    private Integer cantidadSublotesRaw;

    public LoteViewModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getCompraId() {
        return compraId;
    }

    public void setCompraId(String compraId) {
        this.compraId = compraId;
    }

    public String getCantidadSublotes() {
        return cantidadSublotes;
    }

    public void setCantidadSublotes(String cantidadSublotes) {
        this.cantidadSublotes = cantidadSublotes;
    }

    public Integer getIdRaw() {
        return idRaw;
    }

    public void setIdRaw(Integer idRaw) {
        this.idRaw = idRaw;
    }

    public Integer getCompraIdRaw() {
        return compraIdRaw;
    }

    public void setCompraIdRaw(Integer compraIdRaw) {
        this.compraIdRaw = compraIdRaw;
    }

    public Integer getCantidadSublotesRaw() {
        return cantidadSublotesRaw;
    }

    public void setCantidadSublotesRaw(Integer cantidadSublotesRaw) {
        this.cantidadSublotesRaw = cantidadSublotesRaw;
    }
}
