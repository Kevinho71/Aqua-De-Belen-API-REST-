package com.perfumeria.aquadebelen.aquadebelen.inventario.DTO;


public record ProveedorDTORequest(

        String nombre,
        String correo,
        String telefono,
        String nit,
        Integer ubicacionId) {

}
