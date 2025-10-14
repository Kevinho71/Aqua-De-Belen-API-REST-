package com.perfumeria.aquadebelen.aquadebelen.inventario.DTO;

public record ProductoDTORequest (
    String nombre,
    Double precio,
    String descripcion,
    Integer tipoProductoId
    ){

}
