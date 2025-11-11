package com.perfumeria.aquadebelen.aquadebelen.clientes.viewmodel;

public class ClienteViewModel {
    private String id;
    private String nombreCompleto;
    private String telefono;
    private String nitCi;
    private String direccion;
    private String nivelFidelidad;
    private String ubicacion;

    public ClienteViewModel() {
    }

    public ClienteViewModel(String id, String nombreCompleto, String telefono, String nitCi, 
                           String direccion, String nivelFidelidad, String ubicacion) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.telefono = telefono;
        this.nitCi = nitCi;
        this.direccion = direccion;
        this.nivelFidelidad = nivelFidelidad;
        this.ubicacion = ubicacion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNitCi() {
        return nitCi;
    }

    public void setNitCi(String nitCi) {
        this.nitCi = nitCi;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNivelFidelidad() {
        return nivelFidelidad;
    }

    public void setNivelFidelidad(String nivelFidelidad) {
        this.nivelFidelidad = nivelFidelidad;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
}
