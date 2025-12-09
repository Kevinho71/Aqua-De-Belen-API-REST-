package com.perfumeria.aquadebelen.aquadebelen.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoStockDTO {
    private Integer productoId;
    private String nombreProducto;
    private Double stockTotal;
}
