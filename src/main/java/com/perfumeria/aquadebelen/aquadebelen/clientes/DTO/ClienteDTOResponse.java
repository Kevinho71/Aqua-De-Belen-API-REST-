package com.perfumeria.aquadebelen.aquadebelen.clientes.DTO;

public record ClienteDTOResponse(
    Integer id,
    String nombre,
    String apellido,
    String telefono,
    String nitCi,
    String direccion,
    String nivelFidelidad,
    String ubicacion
) {
}
