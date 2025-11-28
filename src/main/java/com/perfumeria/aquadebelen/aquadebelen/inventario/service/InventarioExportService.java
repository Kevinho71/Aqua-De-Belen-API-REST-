package com.perfumeria.aquadebelen.aquadebelen.inventario.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.perfumeria.aquadebelen.aquadebelen.compras.model.PrecioHistorico;
import com.perfumeria.aquadebelen.aquadebelen.compras.repository.PrecioHistoricoDAO;
import com.perfumeria.aquadebelen.aquadebelen.inventario.model.Producto;
import com.perfumeria.aquadebelen.aquadebelen.inventario.repository.ProductoDAO;
import com.perfumeria.aquadebelen.aquadebelen.inventario.repository.SubloteDAO;

@Service
public class InventarioExportService {

    private final ProductoDAO productoDAO;
    private final SubloteDAO subloteDAO;
    private final PrecioHistoricoDAO precioHistoricoDAO;
    
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public InventarioExportService(ProductoDAO productoDAO, SubloteDAO subloteDAO, 
                                   PrecioHistoricoDAO precioHistoricoDAO) {
        this.productoDAO = productoDAO;
        this.subloteDAO = subloteDAO;
        this.precioHistoricoDAO = precioHistoricoDAO;
    }

    public byte[] generarReporteInventario() throws IOException {
        List<Producto> productos = productoDAO.list();
        
        try (Workbook workbook = new XSSFWorkbook(); 
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            
            Sheet sheet = workbook.createSheet("Inventario Continuo");
            
            // Estilos
            CellStyle headerStyle = crearEstiloEncabezado(workbook);
            CellStyle dataStyle = crearEstiloDatos(workbook);
            CellStyle dateStyle = crearEstiloFecha(workbook);
            CellStyle currencyStyle = crearEstiloMoneda(workbook);
            
            // Encabezado
            Row headerRow = sheet.createRow(0);
            String[] columnas = {"ID", "Nombre Producto", "Tipo de Producto", "Descripción", 
                                "Stock Actual", "Último Precio", "Fecha Precio", "Estado"};
            
            for (int i = 0; i < columnas.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columnas[i]);
                cell.setCellStyle(headerStyle);
            }
            
            // Datos
            int rowNum = 1;
            for (Producto producto : productos) {
                Row row = sheet.createRow(rowNum++);
                
                // ID
                Cell cellId = row.createCell(0);
                cellId.setCellValue(producto.getId());
                cellId.setCellStyle(dataStyle);
                
                // Nombre
                Cell cellNombre = row.createCell(1);
                cellNombre.setCellValue(producto.getNombre());
                cellNombre.setCellStyle(dataStyle);
                
                // Tipo de Producto
                Cell cellTipo = row.createCell(2);
                String tipoProducto = producto.getTipoProducto() != null ? 
                    producto.getTipoProducto().getNombre() : "N/A";
                cellTipo.setCellValue(tipoProducto);
                cellTipo.setCellStyle(dataStyle);
                
                // Descripción
                Cell cellDesc = row.createCell(3);
                cellDesc.setCellValue(producto.getDescripcion() != null ? producto.getDescripcion() : "");
                cellDesc.setCellStyle(dataStyle);
                
                // Stock Actual
                Cell cellStock = row.createCell(4);
                Double stockActual = subloteDAO.sumCantidadActualByProductoId(producto.getId());
                cellStock.setCellValue(stockActual != null ? stockActual : 0.0);
                cellStock.setCellStyle(dataStyle);
                
                // Último Precio
                Cell cellPrecio = row.createCell(5);
                PrecioHistorico ultimoPrecio = precioHistoricoDAO.findUltimoPrecioByProductoId(producto.getId());
                if (ultimoPrecio != null) {
                    cellPrecio.setCellValue(ultimoPrecio.getPrecioVenta());
                    cellPrecio.setCellStyle(currencyStyle);
                } else {
                    cellPrecio.setCellValue("N/A");
                    cellPrecio.setCellStyle(dataStyle);
                }
                
                // Fecha del Precio
                Cell cellFecha = row.createCell(6);
                if (ultimoPrecio != null && ultimoPrecio.getFecha() != null) {
                    cellFecha.setCellValue(ultimoPrecio.getFecha().format(FORMATTER));
                    cellFecha.setCellStyle(dateStyle);
                } else {
                    cellFecha.setCellValue("N/A");
                    cellFecha.setCellStyle(dataStyle);
                }
                
                // Estado
                Cell cellEstado = row.createCell(7);
                String estado = producto.isDescontinuado() ? "DESCONTINUADO" : "ACTIVO";
                cellEstado.setCellValue(estado);
                cellEstado.setCellStyle(dataStyle);
            }
            
            // Ajustar ancho de columnas
            for (int i = 0; i < columnas.length; i++) {
                sheet.autoSizeColumn(i);
            }
            
            workbook.write(outputStream);
            return outputStream.toByteArray();
        }
    }

    private CellStyle crearEstiloEncabezado(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 12);
        font.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        return style;
    }

    private CellStyle crearEstiloDatos(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        return style;
    }

    private CellStyle crearEstiloFecha(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        return style;
    }

    private CellStyle crearEstiloMoneda(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        DataFormat format = workbook.createDataFormat();
        style.setDataFormat(format.getFormat("Bs #,##0.00"));
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        return style;
    }
}
