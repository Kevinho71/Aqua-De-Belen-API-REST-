package com.perfumeria.aquadebelen.aquadebelen.compras.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.perfumeria.aquadebelen.aquadebelen.compras.model.Compra;
import com.perfumeria.aquadebelen.aquadebelen.compras.model.DetalleCompra;
import com.perfumeria.aquadebelen.aquadebelen.compras.model.Movimiento;
import com.perfumeria.aquadebelen.aquadebelen.compras.repository.MovimientoDAO;
import com.perfumeria.aquadebelen.aquadebelen.inventario.model.Producto;
import com.perfumeria.aquadebelen.aquadebelen.ventas.model.DetalleVenta;
import com.perfumeria.aquadebelen.aquadebelen.ventas.model.Venta;

@ExtendWith(MockitoExtension.class)
@DisplayName("MovimientoService - Tests Unitarios")
class MovimientoServiceTest {

    @Mock
    private MovimientoDAO movimientoDAO;
    
    @InjectMocks
    private MovimientoService movimientoService;

    private DetalleCompra detalleCompraMock;
    private DetalleVenta detalleVentaMock;
    private Compra compraMock;
    private Venta ventaMock;
    private Producto productoMock;

    @BeforeEach
    void setUp() {
        productoMock = new Producto();
        productoMock.setId(1);
        productoMock.setNombre("Shampoo Dove");

        compraMock = new Compra();
        compraMock.setId(10);
        
        ventaMock = new Venta();
        ventaMock.setId(20);

        detalleCompraMock = new DetalleCompra();
        detalleCompraMock.setId(1);
        detalleCompraMock.setCantidad(50.0);
        detalleCompraMock.setCostoUnitario(15.00);
        detalleCompraMock.setSubtotal(750.00);
        detalleCompraMock.setCompra(compraMock);
        detalleCompraMock.setProducto(productoMock);

        detalleVentaMock = new DetalleVenta();
        detalleVentaMock.setId(2);
        detalleVentaMock.setCantidad(5.0);
        detalleVentaMock.setSubtotal(125.00);
        detalleVentaMock.setVenta(ventaMock);
        detalleVentaMock.setProducto(productoMock);
    }

    @Test
    @DisplayName("Debe crear movimiento tipo COMPRA con datos correctos")
    void crearMovimientoCompra_datosValidos_creaMovimientoEntrada() {
        // Given
        when(movimientoDAO.nextId()).thenReturn(100);
        ArgumentCaptor<Movimiento> movimientoCaptor = ArgumentCaptor.forClass(Movimiento.class);

        // When
        movimientoService.crearMovimientoCompra(detalleCompraMock);

        // Then
        verify(movimientoDAO).store(movimientoCaptor.capture());
        Movimiento movimiento = movimientoCaptor.getValue();
        
        assertEquals(100, movimiento.getId());
        assertEquals(50.0, movimiento.getCantidad());
        assertEquals(15.00, movimiento.getCostoUnitario());
        assertEquals(750.00, movimiento.getCostoTotal());
        assertEquals(0, movimiento.getPrecioUnitario());
        assertEquals(0, movimiento.getPrecioTotal());
        assertEquals("COMPRA", movimiento.getReferenciaTipo());
        assertEquals(detalleCompraMock, movimiento.getDetalleCompraId());
        assertEquals(10, movimiento.getReferenciaId());
        assertNotNull(movimiento.getFecha());
    }

    @Test
    @DisplayName("Debe establecer precio en 0 para movimientos de COMPRA")
    void crearMovimientoCompra_preciosCero_auditoriaEntrada() {
        // Given
        when(movimientoDAO.nextId()).thenReturn(1);
        ArgumentCaptor<Movimiento> movimientoCaptor = ArgumentCaptor.forClass(Movimiento.class);

        // When
        movimientoService.crearMovimientoCompra(detalleCompraMock);

        // Then
        verify(movimientoDAO).store(movimientoCaptor.capture());
        Movimiento movimiento = movimientoCaptor.getValue();
        
        assertEquals(0, movimiento.getPrecioUnitario(), "Precio unitario debe ser 0 en COMPRA");
        assertEquals(0, movimiento.getPrecioTotal(), "Precio total debe ser 0 en COMPRA");
    }

    @Test
    @DisplayName("Debe crear movimiento tipo VENTA con datos correctos")
    void crearMovimientoVenta_datosValidos_creaMovimientoSalida() {
        // Given
        double precioUnitario = 25.00;
        when(movimientoDAO.nextId()).thenReturn(200);
        ArgumentCaptor<Movimiento> movimientoCaptor = ArgumentCaptor.forClass(Movimiento.class);

        // When
        movimientoService.crearMovimientoVenta(detalleVentaMock, precioUnitario);

        // Then
        verify(movimientoDAO).store(movimientoCaptor.capture());
        Movimiento movimiento = movimientoCaptor.getValue();
        
        assertEquals(200, movimiento.getId());
        assertEquals(5.0, movimiento.getCantidad());
        assertEquals(25.00, movimiento.getPrecioUnitario());
        assertEquals(125.00, movimiento.getPrecioTotal());
        assertEquals(0, movimiento.getCostoUnitario());
        assertEquals(0, movimiento.getCostoTotal());
        assertEquals("VENTA", movimiento.getReferenciaTipo());
        assertEquals(detalleVentaMock, movimiento.getDetalleVentaId());
        assertEquals(20, movimiento.getReferenciaId());
        assertNotNull(movimiento.getFecha());
    }

    @Test
    @DisplayName("Debe establecer costo en 0 para movimientos de VENTA")
    void crearMovimientoVenta_costosCero_auditoriaSalida() {
        // Given
        when(movimientoDAO.nextId()).thenReturn(1);
        ArgumentCaptor<Movimiento> movimientoCaptor = ArgumentCaptor.forClass(Movimiento.class);

        // When
        movimientoService.crearMovimientoVenta(detalleVentaMock, 25.00);

        // Then
        verify(movimientoDAO).store(movimientoCaptor.capture());
        Movimiento movimiento = movimientoCaptor.getValue();
        
        assertEquals(0, movimiento.getCostoUnitario(), "Costo unitario debe ser 0 en VENTA");
        assertEquals(0, movimiento.getCostoTotal(), "Costo total debe ser 0 en VENTA");
    }

    @Test
    @DisplayName("Debe asignar ID secuencial desde MovimientoDAO.nextId()")
    void crearMovimientoCompra_asignaIDDesdeDAO() {
        // Given
        when(movimientoDAO.nextId()).thenReturn(500);
        ArgumentCaptor<Movimiento> movimientoCaptor = ArgumentCaptor.forClass(Movimiento.class);

        // When
        movimientoService.crearMovimientoCompra(detalleCompraMock);

        // Then
        verify(movimientoDAO).nextId();
        verify(movimientoDAO).store(movimientoCaptor.capture());
        assertEquals(500, movimientoCaptor.getValue().getId());
    }

    @Test
    @DisplayName("Debe establecer fecha automática en el momento de creación")
    void crearMovimientoCompra_estableceFechaActual() {
        // Given
        LocalDateTime antes = LocalDateTime.now().minusSeconds(1);
        when(movimientoDAO.nextId()).thenReturn(1);
        ArgumentCaptor<Movimiento> movimientoCaptor = ArgumentCaptor.forClass(Movimiento.class);

        // When
        movimientoService.crearMovimientoCompra(detalleCompraMock);

        // Then
        verify(movimientoDAO).store(movimientoCaptor.capture());
        LocalDateTime despues = LocalDateTime.now().plusSeconds(1);
        LocalDateTime fechaMovimiento = movimientoCaptor.getValue().getFecha();
        
        assertTrue(fechaMovimiento.isAfter(antes) && fechaMovimiento.isBefore(despues),
                "La fecha debe ser el momento actual");
    }

    @Test
    @DisplayName("Debe referenciar correctamente el ID de la compra padre")
    void crearMovimientoCompra_referenciaCompraId() {
        // Given
        when(movimientoDAO.nextId()).thenReturn(1);
        ArgumentCaptor<Movimiento> movimientoCaptor = ArgumentCaptor.forClass(Movimiento.class);

        // When
        movimientoService.crearMovimientoCompra(detalleCompraMock);

        // Then
        verify(movimientoDAO).store(movimientoCaptor.capture());
        Movimiento movimiento = movimientoCaptor.getValue();
        
        assertEquals(10, movimiento.getReferenciaId(), "Debe referenciar compra.id");
        assertEquals(detalleCompraMock.getCompra().getId(), movimiento.getReferenciaId());
    }

    @Test
    @DisplayName("Debe referenciar correctamente el ID de la venta padre")
    void crearMovimientoVenta_referenciaVentaId() {
        // Given
        when(movimientoDAO.nextId()).thenReturn(1);
        ArgumentCaptor<Movimiento> movimientoCaptor = ArgumentCaptor.forClass(Movimiento.class);

        // When
        movimientoService.crearMovimientoVenta(detalleVentaMock, 25.00);

        // Then
        verify(movimientoDAO).store(movimientoCaptor.capture());
        Movimiento movimiento = movimientoCaptor.getValue();
        
        assertEquals(20, movimiento.getReferenciaId(), "Debe referenciar venta.id");
        assertEquals(detalleVentaMock.getVenta().getId(), movimiento.getReferenciaId());
    }

    @Test
    @DisplayName("Debe persistir el movimiento de compra en la base de datos")
    void crearMovimientoCompra_persisteEnDB() {
        // Given
        when(movimientoDAO.nextId()).thenReturn(1);

        // When
        movimientoService.crearMovimientoCompra(detalleCompraMock);

        // Then
        verify(movimientoDAO, times(1)).store(any(Movimiento.class));
    }

    @Test
    @DisplayName("Debe persistir el movimiento de venta en la base de datos")
    void crearMovimientoVenta_persisteEnDB() {
        // Given
        when(movimientoDAO.nextId()).thenReturn(1);

        // When
        movimientoService.crearMovimientoVenta(detalleVentaMock, 25.00);

        // Then
        verify(movimientoDAO, times(1)).store(any(Movimiento.class));
    }
}
