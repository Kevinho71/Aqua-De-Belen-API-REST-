package com.perfumeria.aquadebelen.aquadebelen.inventario.viewmodel;

public class ProductoViewModel {

    private String productoId;
    private String nombre;
    private String precio;
    private String descripcion;
    private String tipoProducto;
    private Integer tipoProductoId;
    private boolean descontinuado;

    public ProductoViewModel() {
    }

    public ProductoViewModel(String productoId, String nombre, String precio, String descripcion, String tipoProducto, Integer tipoProductoId, boolean descontinuado) {
        this.productoId = productoId;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.tipoProducto = tipoProducto;
        this.tipoProductoId = tipoProductoId;
        this.descontinuado = descontinuado;
    }

    public String getProductoId() {
        return productoId;
    }

    public void setProductoId(String productoId) {
        this.productoId = productoId;
    }

    public boolean isDescontinuado() {
        return descontinuado;
    }

    public void setDescontinuado(boolean descontinuado) {
        this.descontinuado = descontinuado;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(String tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public Integer getTipoProductoId() {
        return tipoProductoId;
    }

    public void setTipoProductoId(Integer tipoProductoId) {
        this.tipoProductoId = tipoProductoId;
    }

}
