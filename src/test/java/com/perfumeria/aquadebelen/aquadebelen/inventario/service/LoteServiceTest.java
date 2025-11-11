package com.perfumeria.aquadebelen.aquadebelen.inventario.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.perfumeria.aquadebelen.aquadebelen.compras.model.Compra;
import com.perfumeria.aquadebelen.aquadebelen.compras.model.DetalleCompra;
import com.perfumeria.aquadebelen.aquadebelen.inventario.model.EstadoSublote;
import com.perfumeria.aquadebelen.aquadebelen.inventario.model.Lote;
import com.perfumeria.aquadebelen.aquadebelen.inventario.model.Producto;
import com.perfumeria.aquadebelen.aquadebelen.inventario.model.Sublote;
import com.perfumeria.aquadebelen.aquadebelen.inventario.repository.LoteDAO;
import com.perfumeria.aquadebelen.aquadebelen.inventario.repository.SubloteDAO;

@ExtendWith(MockitoExtension.class)
@DisplayName("LoteService - Tests Unitarios")
class LoteServiceTest {

    @Mock
    private LoteDAO loteDAO;
    
    @Mock
    private SubloteDAO subloteDAO;
    
    @InjectMocks
    private LoteService loteService;

    private Producto productoMock;
    private DetalleCompra detalleCompra;

    @BeforeEach
    void setUp() {
        productoMock = new Producto();
        productoMock.setId(5);
        productoMock.setNombre("Producto Test");
        
        detalleCompra = new DetalleCompra();
        detalleCompra.setId(10);
        detalleCompra.setCantidad(50.0);
        detalleCompra.setCostoUnitario(25.0);
        detalleCompra.setProducto(productoMock);
        detalleCompra.setFechaVencimiento(LocalDate.of(2026, 12, 31));
    }

    @Test
    @DisplayName("Debe crear sublotes y establecer relación con DetalleCompra")
    void crearSublotes_estableceRelacionConDetalleCompra() {
        // Given
        Lote lote = new Lote();
        lote.setId(1);
        List<DetalleCompra> detalles = List.of(detalleCompra);
        
        when(subloteDAO.nextId()).thenReturn(100);

        // When
        loteService.crearSublotes(lote, detalles);

        // Then
        assertEquals(1, lote.getSublotes().size());
        Sublote sublote = lote.getSublotes().get(0);
        
        // ⚠️ CRÍTICO: Verificar que la relación DetalleCompra ↔ Sublote esté establecida
        assertNotNull(sublote.getDetalleCompra(), 
            "Sublote DEBE tener referencia a DetalleCompra");
        assertEquals(detalleCompra.getId(), sublote.getDetalleCompra().getId(),
            "ID del DetalleCompra debe coincidir");
    }

    @Test
    @DisplayName("Debe copiar cantidad del detalle a cantidad inicial y actual del sublote")
    void crearSublotes_copiaCantidadesCorrectamente() {
        // Given
        Lote lote = new Lote();
        lote.setId(1);
        detalleCompra.setCantidad(75.5);
        List<DetalleCompra> detalles = List.of(detalleCompra);
        
        when(subloteDAO.nextId()).thenReturn(100);

        // When
        loteService.crearSublotes(lote, detalles);

        // Then
        Sublote sublote = lote.getSublotes().get(0);
        assertEquals(75.5, sublote.getCantidadInicial(), 0.001,
            "Cantidad inicial debe ser igual a cantidad del detalle");
        assertEquals(75.5, sublote.getCantidadActual(), 0.001,
            "Cantidad actual debe iniciar igual a cantidad inicial");
    }

    @Test
    @DisplayName("Debe usar fecha de vencimiento del detalle, NO calcularla")
    void crearSublotes_usaFechaVencimientoDelDetalle() {
        // Given
        Lote lote = new Lote();
        lote.setId(1);
        LocalDate fechaEsperada = LocalDate.of(2027, 6, 15);
        detalleCompra.setFechaVencimiento(fechaEsperada);
        List<DetalleCompra> detalles = List.of(detalleCompra);
        
        when(subloteDAO.nextId()).thenReturn(100);

        // When
        loteService.crearSublotes(lote, detalles);

        // Then
        Sublote sublote = lote.getSublotes().get(0);
        assertEquals(fechaEsperada, sublote.getFechaVencimiento(),
            "Fecha de vencimiento debe venir del detalle de compra, NO ser calculada");
    }

    @Test
    @DisplayName("Debe establecer estado inicial como DISPONIBLE")
    void crearSublotes_estadoInicialDisponible() {
        // Given
        Lote lote = new Lote();
        lote.setId(1);
        List<DetalleCompra> detalles = List.of(detalleCompra);
        
        when(subloteDAO.nextId()).thenReturn(100);

        // When
        loteService.crearSublotes(lote, detalles);

        // Then
        Sublote sublote = lote.getSublotes().get(0);
        assertEquals(EstadoSublote.DISPONIBLE, sublote.getEstado(),
            "Estado inicial del sublote debe ser DISPONIBLE");
    }

    @Test
    @DisplayName("Debe asignar IDs secuenciales a múltiples sublotes")
    void crearSublotes_asignaIDsSecuenciales() {
        // Given
        Lote lote = new Lote();
        lote.setId(1);
        
        DetalleCompra detalle1 = crearDetalleCompra(10, 100.0);
        DetalleCompra detalle2 = crearDetalleCompra(20, 200.0);
        DetalleCompra detalle3 = crearDetalleCompra(30, 300.0);
        
        List<DetalleCompra> detalles = List.of(detalle1, detalle2, detalle3);
        
        when(subloteDAO.nextId()).thenReturn(500);

        // When
        loteService.crearSublotes(lote, detalles);

        // Then
        assertEquals(3, lote.getSublotes().size());
        assertEquals(500, lote.getSublotes().get(0).getId());
        assertEquals(501, lote.getSublotes().get(1).getId());
        assertEquals(502, lote.getSublotes().get(2).getId());
    }

    @Test
    @DisplayName("Debe generar código de sublote único con formato correcto")
    void crearSublotes_generaCodigoSubloteUnico() {
        // Given
        Lote lote = new Lote();
        lote.setId(42);
        detalleCompra.getProducto().setId(99);
        List<DetalleCompra> detalles = List.of(detalleCompra);
        
        when(subloteDAO.nextId()).thenReturn(100);

        // When
        loteService.crearSublotes(lote, detalles);

        // Then
        Sublote sublote = lote.getSublotes().get(0);
        String codigoSublote = sublote.getCodigoSublote();
        
        assertNotNull(codigoSublote);
        assertTrue(codigoSublote.startsWith("SL-42-99-"),
            "Código debe seguir formato: SL-{loteId}-{productoId}-{timestamp}");
        assertTrue(codigoSublote.length() > 15,
            "Código debe incluir timestamp para unicidad");
    }

    @Test
    @DisplayName("Debe establecer fecha de producción como fecha actual")
    void crearSublotes_estableceFechaProduccionActual() {
        // Given
        Lote lote = new Lote();
        lote.setId(1);
        List<DetalleCompra> detalles = List.of(detalleCompra);
        
        when(subloteDAO.nextId()).thenReturn(100);
        LocalDate hoy = LocalDate.now();

        // When
        loteService.crearSublotes(lote, detalles);

        // Then
        Sublote sublote = lote.getSublotes().get(0);
        assertEquals(hoy, sublote.getFechaProduccion(),
            "Fecha de producción debe ser la fecha actual");
    }

    @Test
    @DisplayName("Debe establecer relación bidireccional Lote ↔ Sublote")
    void crearSublotes_estableceRelacionBidireccionalConLote() {
        // Given
        Lote lote = new Lote();
        lote.setId(1);
        List<DetalleCompra> detalles = List.of(detalleCompra);
        
        when(subloteDAO.nextId()).thenReturn(100);

        // When
        loteService.crearSublotes(lote, detalles);

        // Then
        Sublote sublote = lote.getSublotes().get(0);
        
        // Verificar relación Lote → Sublote
        assertTrue(lote.getSublotes().contains(sublote),
            "Lote debe contener el sublote en su lista");
        
        // Verificar relación Sublote → Lote
        assertNotNull(sublote.getLote(),
            "Sublote debe tener referencia al Lote");
        assertEquals(lote.getId(), sublote.getLote().getId(),
            "ID del lote debe coincidir");
    }

    @Test
    @DisplayName("Debe crear lote con fecha de ingreso de la compra")
    void createLote_estableceFechaIngresoDeCompra() {
        // Given
        Compra compra = new Compra();
        LocalDateTime fechaCompra = LocalDateTime.of(2025, 11, 10, 14, 30);
        compra.setFecha(fechaCompra);
        compra.setDetallesCompra(List.of(detalleCompra));
        
        when(loteDAO.nextId()).thenReturn(50);
        when(subloteDAO.nextId()).thenReturn(100);

        // When
        Lote lote = loteService.createLote(compra);

        // Then
        assertEquals(fechaCompra, lote.getFechaIngreso(),
            "Fecha de ingreso del lote debe ser igual a fecha de la compra");
    }

    // Helper method
    private DetalleCompra crearDetalleCompra(Integer id, double cantidad) {
        DetalleCompra detalle = new DetalleCompra();
        detalle.setId(id);
        detalle.setCantidad(cantidad);
        detalle.setCostoUnitario(10.0);
        detalle.setProducto(productoMock);
        detalle.setFechaVencimiento(LocalDate.now().plusMonths(6));
        return detalle;
    }
}
