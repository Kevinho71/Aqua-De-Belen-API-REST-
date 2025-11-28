package com.perfumeria.aquadebelen.aquadebelen.inventario.viewmodel;

import java.time.LocalDate;

public record PedidoSugeridoViewModel(
    Integer id,
    Integer productoId,
    String productoNombre,
    Integer cantidadSugerida,
    LocalDate fechaSugerida,
    String estado,
    String observacion,
    Double stockActualMomento,
    Integer ropMomento
) {}
