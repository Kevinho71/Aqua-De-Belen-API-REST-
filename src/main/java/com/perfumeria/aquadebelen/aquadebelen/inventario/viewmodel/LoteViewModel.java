package com.perfumeria.aquadebelen.aquadebelen.inventario.viewmodel;

public class LoteViewModel {
    private String id;
    private String fechaIngreso;
    private String compraId;
    private String cantidadSublotes;

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
}
