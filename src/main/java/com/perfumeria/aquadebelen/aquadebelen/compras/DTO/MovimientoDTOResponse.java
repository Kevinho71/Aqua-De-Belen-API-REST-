package com.perfumeria.aquadebelen.aquadebelen.compras.DTO;

public record MovimientoDTOResponse(
    Integer id,
    Double cantidad,
    Double costoUnitario,
    Double precioUnitario,
    Double costoTotal,
    Double precioTotal,
    String fecha,
    String referenciaTipo,
    Integer referenciaId,
    Integer detalleCompraId,
    Integer detalleVentaId
) {
}
