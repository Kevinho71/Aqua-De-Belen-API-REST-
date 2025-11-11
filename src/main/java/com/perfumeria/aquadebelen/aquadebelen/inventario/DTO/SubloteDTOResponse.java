package com.perfumeria.aquadebelen.aquadebelen.inventario.DTO;

public record SubloteDTOResponse(
    Integer id,
    String codigoSublote,
    String producto,
    String fechaVencimiento,
    String fechaProduccion,
    Double cantidadInicial,
    Double cantidadActual,
    Double costoUnitario,
    String estado,
    Integer loteId
) {
}
