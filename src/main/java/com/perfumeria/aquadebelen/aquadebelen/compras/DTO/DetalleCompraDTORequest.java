package com.perfumeria.aquadebelen.aquadebelen.compras.DTO;

import java.time.LocalDate;

public record DetalleCompraDTORequest(

        Integer productoId,
        double costoUnitario,
        double cantidad,
        double descuento,
        LocalDate vencimiento

) {

}
