package com.perfumeria.aquadebelen.aquadebelen.inventario.controller;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perfumeria.aquadebelen.aquadebelen.inventario.service.InventarioExportService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Reportes de Inventario", description = "Exportación de datos de inventario")
public class InventarioReporteController {

    private final InventarioExportService exportService;

    public InventarioReporteController(InventarioExportService exportService) {
        this.exportService = exportService;
    }

    @Operation(
        summary = "Descargar reporte de inventario en Excel",
        description = "Genera un archivo Excel con el recuento actual de todos los productos, incluyendo tipo, precio histórico, descripción y stock actual"
    )
    @GetMapping(value = "/inventario/export/excel", produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public ResponseEntity<byte[]> descargarInventarioExcel() throws IOException {
        byte[] excelData = exportService.generarReporteInventario();
        
        String filename = "Inventario_Continuo_" + 
            java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + 
            ".xlsx";
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentDispositionFormData("attachment", filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        
        return ResponseEntity.ok()
            .headers(headers)
            .body(excelData);
    }
}
