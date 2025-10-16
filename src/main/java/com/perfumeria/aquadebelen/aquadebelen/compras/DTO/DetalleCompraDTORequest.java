package com.perfumeria.aquadebelen.aquadebelen.compras.DTO;

public record DetalleCompraDTORequest(

        double costoUnitario,
        double cantidad,
        double descuento,
        Integer productoId

) {

}
