package com.perfumeria.aquadebelen.aquadebelen.inventario.viewmodel;

public class ListLoteViewModel {
    private Integer id;
    private String fechaIngreso;
    private String compraId;
    private String cantidadSublotes;
    private Integer compraIdRaw;
    private Integer cantidadSublotesRaw;

    public ListLoteViewModel() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
