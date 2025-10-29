package com.perfumeria.aquadebelen.aquadebelen.compras.DTO;

import java.time.LocalDateTime;
import java.util.List;

public record CompraDTOResponse(

    Integer id,
    double costoBruto,
    double costoNeto,
    double descuentoTotal,  
   // Integer loteId,
    String proveedor,
    LocalDateTime fecha,
    List<DetalleCompraDTOResponse> detalles

) {

}
