package com.perfumeria.aquadebelen.aquadebelen.dashboard.viewmodel;

public record InventoryKPIsViewModel(
    Integer productoId,
    String nombreProducto,
    String clasificacionABC,
    Integer eoq,
    Integer puntoReorden,
    Double stockActual,
    String estadoReposicion
) {
    /**
     * Retorna la recomendación de control de inventario según clasificación ABC
     */
    public String recomendacion() {
        return switch(clasificacionABC) {
            case "A" -> "Revisión Continua (Sistema Q) - Control estricto, monitoreo diario";
            case "B" -> "Revisión Periódica (Sistema P) - Control moderado, revisión semanal";
            case "C" -> "Compras Económicas - Lotes grandes, revisión mensual";
            default -> "Sin clasificación";
        };
    }
}
