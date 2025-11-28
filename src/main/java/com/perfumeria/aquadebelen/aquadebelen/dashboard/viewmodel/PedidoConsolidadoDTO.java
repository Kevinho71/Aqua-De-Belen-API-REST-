package com.perfumeria.aquadebelen.aquadebelen.dashboard.viewmodel;

import java.util.List;
import java.util.Map;

public record PedidoConsolidadoDTO(
    Integer proveedorId,
    String proveedorNombre,
    int cantidadPedidos,
    List<Map<String, Object>> productos,
    AhorroConsolidacionDTO ahorro
) {}
