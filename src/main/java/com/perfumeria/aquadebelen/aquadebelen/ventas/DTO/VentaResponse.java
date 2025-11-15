package com.perfumeria.aquadebelen.aquadebelen.ventas.DTO;

import java.util.List;


public record VentaResponse(
     Integer ventaId,
     String cliente,
    double totalBruto,
    double descuentoTotal,
    double totalNeto,
    Boolean conFactura,
    String fecha,
    List<DetalleVentaResponse> detalles
) {
   
}
