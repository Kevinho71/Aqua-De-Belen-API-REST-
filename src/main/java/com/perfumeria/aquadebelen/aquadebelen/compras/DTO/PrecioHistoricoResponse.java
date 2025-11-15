package com.perfumeria.aquadebelen.aquadebelen.compras.DTO;

public record PrecioHistoricoResponse(
    Integer id,
    Integer productoId,
    String nombreProducto,
    Double precioVenta,
    String fecha
) {
}
