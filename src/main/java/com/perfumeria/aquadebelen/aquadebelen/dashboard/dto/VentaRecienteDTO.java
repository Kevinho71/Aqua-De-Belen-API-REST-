package com.perfumeria.aquadebelen.aquadebelen.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VentaRecienteDTO {
    private Integer ventaId;
    private String nombreCliente;
    private LocalDateTime fechaVenta;
    private Double totalVenta;
}
