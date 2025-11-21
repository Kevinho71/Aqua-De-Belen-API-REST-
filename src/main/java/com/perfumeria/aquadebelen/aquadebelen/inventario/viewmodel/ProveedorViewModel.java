package com.perfumeria.aquadebelen.aquadebelen.inventario.viewmodel;

public class ProveedorViewModel {
    private Integer id;
    private String nombre;
    private String correo;
    private String telefono;
    private String nit;
    private Integer ubicacionId;
    private String ciudad;
    private String zona;
    private String ubicacion;

    public ProveedorViewModel() {
    }

    public ProveedorViewModel(Integer id, String nombre, String correo, String telefono, String nit,
            Integer ubicacionId, String ciudad, String zona, String ubicacion) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.nit = nit;
        this.ubicacionId = ubicacionId;
        this.ciudad = ciudad;
        this.zona = zona;
        this.ubicacion = ubicacion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public Integer getUbicacionId() {
        return ubicacionId;
    }

    public void setUbicacionId(Integer ubicacionId) {
        this.ubicacionId = ubicacionId;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
}
