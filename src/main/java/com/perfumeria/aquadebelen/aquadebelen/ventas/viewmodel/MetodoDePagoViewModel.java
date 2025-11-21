package com.perfumeria.aquadebelen.aquadebelen.ventas.viewmodel;

public class MetodoDePagoViewModel {
    private Integer id;
    private String metodo;

    public MetodoDePagoViewModel() {
    }

    public MetodoDePagoViewModel(Integer id, String metodo) {
        this.id = id;
        this.metodo = metodo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }
}
