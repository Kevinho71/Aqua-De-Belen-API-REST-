package com.perfumeria.aquadebelen.aquadebelen.inventario.DTO;

public record ProductoDTORequest (
    String nombre,
    double precio,
    String descripcion,
    Integer tipoProductoId,
    Boolean descontinuado
    ){

}
