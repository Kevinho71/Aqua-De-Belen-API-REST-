package com.perfumeria.aquadebelen.aquadebelen.clientes.viewmodel;

public class ListClienteViewModel {

    private Integer id;
    private String nombre;
    private String apellido;
    private String nombreCompleto;
    private String telefono;
    private String nitCi;
    private String direccion;
    private String nivelFidelidad;
    private Integer nivelFidelidadId;
    private String ubicacion;
    private Integer ubicacionId;

    public ListClienteViewModel() {
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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
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

    public Integer getNivelFidelidadId() {
        return nivelFidelidadId;
    }

    public void setNivelFidelidadId(Integer nivelFidelidadId) {
        this.nivelFidelidadId = nivelFidelidadId;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Integer getUbicacionId() {
        return ubicacionId;
    }

    public void setUbicacionId(Integer ubicacionId) {
        this.ubicacionId = ubicacionId;
    }
}
