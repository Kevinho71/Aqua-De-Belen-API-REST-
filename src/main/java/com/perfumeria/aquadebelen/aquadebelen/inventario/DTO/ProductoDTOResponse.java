package com.perfumeria.aquadebelen.aquadebelen.inventario.DTO;


public record ProductoDTOResponse (
    Integer productoId,
    Double precio,
    String descripcion,
    String nombre,
    String tipoProducto,
    Integer tipoProductoId
){

}
