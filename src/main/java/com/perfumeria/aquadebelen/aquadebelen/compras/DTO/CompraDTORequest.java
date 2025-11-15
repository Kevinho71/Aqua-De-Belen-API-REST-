package com.perfumeria.aquadebelen.aquadebelen.compras.DTO;

import java.util.List;


public record CompraDTORequest(

        Integer proveedorId,
        List<DetalleCompraDTORequest> detalles
        ) {

}
