package com.perfumeria.aquadebelen.aquadebelen.inventario.DTO;

public record LoteDTOResponse(
    Integer id,
    String fechaIngreso,
    Integer compraId,
    Integer cantidadSublotes
) {
}
