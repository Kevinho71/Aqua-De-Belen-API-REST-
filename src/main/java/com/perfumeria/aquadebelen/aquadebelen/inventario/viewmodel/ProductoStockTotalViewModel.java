package com.perfumeria.aquadebelen.aquadebelen.inventario.viewmodel;

public class ProductoStockTotalViewModel {

    private final Integer id;
    private final String nombre;
    private final double cantidadTotal;

    public ProductoStockTotalViewModel(Integer id, String nombre, double cantidadTotal) {
        this.id = id;
        this.nombre = nombre;
        this.cantidadTotal = cantidadTotal;
    }

    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getCantidadTotal() {
        return cantidadTotal;
    }
}
