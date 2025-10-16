package com.perfumeria.aquadebelen.aquadebelen.inventario.DTO;

import java.time.LocalDate;

public record ProductoDTOResponse (
    Integer productoId,
    Double precio,
    String descripcion,
    String nombre,
    String tipoProducto
){

}
