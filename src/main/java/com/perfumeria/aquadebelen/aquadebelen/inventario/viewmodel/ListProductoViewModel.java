package com.perfumeria.aquadebelen.aquadebelen.inventario.viewmodel;

public class ListProductoViewModel {
    private Integer id;
    private String nombre;
    private String precio;
    private String descripcion;
    private String tipoProducto;
    private boolean descontinuado;

    public ListProductoViewModel(){}
    
    public ListProductoViewModel(Integer id, String nombre, String precio, String descripcion, String tipoProducto, boolean descontinuado) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.tipoProducto = tipoProducto;
        this.descontinuado = descontinuado;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public boolean isDescontinuado() {
        return descontinuado;
    }
    public void setDescontinuado(boolean descontinuado) {
        this.descontinuado = descontinuado;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
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
    public String getTipoProducto() {
        return tipoProducto;
    }
    public void setTipoProducto(String tipoProducto) {
        this.tipoProducto = tipoProducto;
    }


    
}
