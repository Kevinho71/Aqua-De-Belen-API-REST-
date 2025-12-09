package com.perfumeria.aquadebelen.aquadebelen.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubloteProximoVencerDTO {
    private String nombreProducto;
    private Double cantidad;
    private LocalDate fechaVencimiento;
}
