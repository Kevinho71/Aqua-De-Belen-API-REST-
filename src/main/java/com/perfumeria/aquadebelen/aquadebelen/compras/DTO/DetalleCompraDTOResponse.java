package com.perfumeria.aquadebelen.aquadebelen.compras.DTO;


public record DetalleCompraDTOResponse(
        Integer compraId,
        String producto,
        String tipoProducto,
        double cantidad,
        double costoUnitario,
        double descuento,
        double subtotal
        
       // Integer subloteId


) {

}
