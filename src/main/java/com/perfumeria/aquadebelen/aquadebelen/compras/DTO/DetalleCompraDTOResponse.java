package com.perfumeria.aquadebelen.aquadebelen.compras.DTO;

import com.perfumeria.aquadebelen.aquadebelen.inventario.model.Producto;
import com.perfumeria.aquadebelen.aquadebelen.inventario.model.Sublote;


public record DetalleCompraDTOResponse(

        String producto,
        double costoUnitario,
        double descuento,
        double subtotal,
        Integer subloteId

) {

}
