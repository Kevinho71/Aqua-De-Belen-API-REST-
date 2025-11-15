package com.perfumeria.aquadebelen.aquadebelen.compras.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perfumeria.aquadebelen.aquadebelen.compras.DTO.PrecioHistoricoResponse;
import com.perfumeria.aquadebelen.aquadebelen.compras.service.PrecioHistoricoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;

@Validated
@RequestMapping("/api/v1")
@RestController
@Tag(name = "Precios Históricos", description = "Gestión y consulta del historial de precios de productos")
public class PrecioHistoricoController {

    private final PrecioHistoricoService precioHistoricoService;

    public PrecioHistoricoController(PrecioHistoricoService precioHistoricoService) {
        this.precioHistoricoService = precioHistoricoService;
    }

    @Operation(
        summary = "Listar todos los precios históricos",
        description = "Obtiene el historial completo de todos los cambios de precio de todos los productos, ordenados por fecha (más reciente primero)"
    )
    @ApiResponse(responseCode = "200", description = "Lista de precios históricos obtenida exitosamente")
    @GetMapping("/precios-historicos")
    public ResponseEntity<List<PrecioHistoricoResponse>> listarTodos() {
        List<PrecioHistoricoResponse> precios = precioHistoricoService.listarTodos();
        return ResponseEntity.ok(precios);
    }

    @Operation(
        summary = "Obtener historial de precios de un producto",
        description = "Obtiene todos los cambios de precio de un producto específico, ordenados por fecha (más reciente primero)"
    )
    @ApiResponse(responseCode = "200", description = "Historial de precios del producto obtenido exitosamente")
    @GetMapping("/productos/{productoId}/precios-historicos")
    public ResponseEntity<List<PrecioHistoricoResponse>> listarPorProducto(
        @Parameter(description = "ID del producto", required = true)
        @PathVariable("productoId") @Min(1) Integer productoId) {
        List<PrecioHistoricoResponse> precios = precioHistoricoService.listarPorProducto(productoId);
        return ResponseEntity.ok(precios);
    }

    @Operation(
        summary = "Obtener precio actual de un producto",
        description = "Obtiene el precio de venta más reciente (vigente) de un producto específico"
    )
    @ApiResponse(responseCode = "200", description = "Precio actual obtenido exitosamente")
    @GetMapping("/productos/{productoId}/precio-actual")
    public ResponseEntity<PrecioHistoricoResponse> obtenerPrecioActual(
        @Parameter(description = "ID del producto", required = true)
        @PathVariable("productoId") @Min(1) Integer productoId) {
        PrecioHistoricoResponse precio = precioHistoricoService.obtenerPrecioActual(productoId);
        return ResponseEntity.ok(precio);
    }
}
