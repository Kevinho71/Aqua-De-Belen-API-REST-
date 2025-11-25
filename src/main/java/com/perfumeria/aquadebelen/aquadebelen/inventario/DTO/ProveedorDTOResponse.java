package com.perfumeria.aquadebelen.aquadebelen.inventario.DTO;

public record ProveedorDTOResponse(
    Integer id,
    String nombre,
    String correo,
    String telefono,
    String nit,
    Integer ubicacionId,
    String ubicacionCiudad,
    String ubicacionZona
) {

}
