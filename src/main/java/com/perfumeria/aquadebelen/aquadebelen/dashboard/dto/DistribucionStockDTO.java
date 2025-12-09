package com.perfumeria.aquadebelen.aquadebelen.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistribucionStockDTO {
    private String nombreProducto;
    private Double porcentaje;
    private Double stockTotal;
}
