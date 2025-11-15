package com.perfumeria.aquadebelen.aquadebelen.inventario.DTO;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public record ActualizarPrecioRequest(
    
    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    Double precioVenta
) {
}
