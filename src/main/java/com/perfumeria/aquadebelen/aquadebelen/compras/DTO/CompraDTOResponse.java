package com.perfumeria.aquadebelen.aquadebelen.compras.DTO;

import java.util.List;

public record CompraDTOResponse(

    Integer id,
    double costoBruto,
    double costoNeto,
    double descuentoTotal,  
    Integer loteId,
    String proveedor,
    List<DetalleCompraDTOResponse> detalles

) {

}
