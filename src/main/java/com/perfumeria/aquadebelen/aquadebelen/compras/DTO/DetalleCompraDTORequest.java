package com.perfumeria.aquadebelen.aquadebelen.compras.DTO;

public record DetalleCompraDTORequest(

        Integer productoId,
        double costoUnitario,
        double cantidad,
        double descuento
        

) {

}
