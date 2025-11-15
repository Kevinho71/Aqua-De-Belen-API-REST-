package com.perfumeria.aquadebelen.aquadebelen.clientes.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ClienteDTORequest(
    @NotBlank(message = "El nombre es requerido")
    String nombre,
    
    @NotBlank(message = "El apellido es requerido")
    String apellido,
    
    String telefono,
    
    String nitCi,
    
    String direccion,
    
    @NotNull(message = "El nivel de fidelidad es requerido")
    Integer nivelFidelidadId,
    
    @NotNull(message = "La ubicación es requerida")
    Integer ubicacionId
) {
}
