package com.perfumeria.aquadebelen.aquadebelen.ventas.DTO;

public record DetalleVentaResponse(

    Integer ventaId,
    Integer detalleId,
    String producto,
    double costoUnitario,
    double cantidad,
    double descuento,
    double subtotal

) {

}
