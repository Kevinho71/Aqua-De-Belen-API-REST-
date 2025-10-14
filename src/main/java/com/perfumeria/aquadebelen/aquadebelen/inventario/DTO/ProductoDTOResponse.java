package com.perfumeria.aquadebelen.aquadebelen.inventario.DTO;

import java.time.LocalDateTime;

public record ProductoDTOResponse (
    Integer productoId,
    Double precio,
    String descripcion,
    String nombre,
    Integer tipoProductoId,
    
    LocalDateTime fechaCaducidad
){

}
