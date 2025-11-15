package com.perfumeria.aquadebelen.aquadebelen.compras.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.perfumeria.aquadebelen.aquadebelen.compras.DTO.DetalleCompraDTORequest;
import com.perfumeria.aquadebelen.aquadebelen.compras.model.Compra;
import com.perfumeria.aquadebelen.aquadebelen.compras.model.DetalleCompra;
import com.perfumeria.aquadebelen.aquadebelen.compras.repository.ComprasDAO;
import com.perfumeria.aquadebelen.aquadebelen.compras.repository.DetalleCompraDAO;
import com.perfumeria.aquadebelen.aquadebelen.inventario.model.Producto;
import com.perfumeria.aquadebelen.aquadebelen.inventario.repository.ProductoDAO;
import com.perfumeria.aquadebelen.aquadebelen.inventario.repository.ProveedorDAO;
import com.perfumeria.aquadebelen.aquadebelen.inventario.service.LoteService;

@ExtendWith(MockitoExtension.class)
@DisplayName("CompraService - Tests Unitarios")
class CompraServiceTest {

    @Mock
    private ComprasDAO comprasDAO;
    
    @Mock
    private ProveedorDAO proveedorDAO;
    
    @Mock
    private ProductoDAO productoDAO;
    
    @Mock
    private DetalleCompraDAO detalleCompraDAO;
    
    @Mock
    private LoteService loteService;
    
    @Mock
    private MovimientoService movimientoService;
    
    @InjectMocks
    private CompraService compraService;

    private Producto productoMock;

    @BeforeEach
    void setUp() {
        productoMock = new Producto();
        productoMock.setId(1);
        productoMock.setNombre("Producto Test");
    }

    @Test
    @DisplayName("Debe calcular costo bruto correctamente con un solo producto")
    void calcularCostoBruto_unProducto_retornaSumaCorrecta() {
        // Given
        DetalleCompraDTORequest detalle = new DetalleCompraDTORequest(
            1,           // productoId
            10.50,       // costoUnitario
            5.0,         // cantidad
            0.0,         // descuento
            LocalDate.now().plusMonths(6) // vencimiento
        );
        List<DetalleCompraDTORequest> detalles = List.of(detalle);

        // When
        double costoBruto = compraService.calcularCostoBruto(detalles);

        // Then
        // (10.50 * 5) = 52.50
        assertEquals(52.50, costoBruto, 0.001, 
            "Costo bruto debe ser precio unitario * cantidad");
    }

    @Test
    @DisplayName("Debe calcular costo bruto correctamente con múltiples productos")
    void calcularCostoBruto_multipleProductos_retornaSumaTotal() {
        // Given
        DetalleCompraDTORequest detalle1 = new DetalleCompraDTORequest(
            1, 10.50, 5.0, 0.0, LocalDate.now().plusMonths(6));
        DetalleCompraDTORequest detalle2 = new DetalleCompraDTORequest(
            2, 20.00, 3.0, 0.0, LocalDate.now().plusMonths(6));
        DetalleCompraDTORequest detalle3 = new DetalleCompraDTORequest(
            3, 15.75, 2.0, 0.0, LocalDate.now().plusMonths(6));
        
        List<DetalleCompraDTORequest> detalles = List.of(detalle1, detalle2, detalle3);

        // When
        double costoBruto = compraService.calcularCostoBruto(detalles);

        // Then
        // (10.50 * 5) + (20.00 * 3) + (15.75 * 2) = 52.50 + 60.00 + 31.50 = 144.00
        assertEquals(144.00, costoBruto, 0.001);
    }

    @Test
    @DisplayName("Debe retornar 0 cuando la lista de detalles está vacía")
    void calcularCostoBruto_listaVacia_retornaCero() {
        // Given
        List<DetalleCompraDTORequest> detalles = new ArrayList<>();

        // When
        double costoBruto = compraService.calcularCostoBruto(detalles);

        // Then
        assertEquals(0.0, costoBruto);
    }

    @Test
    @DisplayName("Debe calcular correctamente con cantidades decimales")
    void calcularCostoBruto_cantidadesDecimales_calculaCorrectamente() {
        // Given
        DetalleCompraDTORequest detalle = new DetalleCompraDTORequest(
            1, 12.50, 3.5, 0.0, LocalDate.now().plusMonths(6));
        List<DetalleCompraDTORequest> detalles = List.of(detalle);

        // When
        double costoBruto = compraService.calcularCostoBruto(detalles);

        // Then
        // 12.50 * 3.5 = 43.75
        assertEquals(43.75, costoBruto, 0.001);
    }

    @Test
    @DisplayName("Debe agregar detalles correctamente y calcular descuento total")
    void agregarDetalles_calculaSubtotalesYDescuentoTotal() {
        // Given
        Compra compra = new Compra();
        
        DetalleCompraDTORequest detalle1 = new DetalleCompraDTORequest(
            1, 100.0, 5.0, 10.0, LocalDate.now().plusMonths(6));
        DetalleCompraDTORequest detalle2 = new DetalleCompraDTORequest(
            2, 50.0, 2.0, 5.0, LocalDate.now().plusMonths(6));
        
        List<DetalleCompraDTORequest> detalles = List.of(detalle1, detalle2);
        
        when(detalleCompraDAO.nextId()).thenReturn(1);
        when(productoDAO.findById(anyInt())).thenReturn(productoMock);

        // When
        compraService.agregarDetalles(detalles, compra);

        // Then
        assertEquals(2, compra.getDetallesCompra().size());
        assertEquals(15.0, compra.getDescuentoTotal(), 0.001, 
            "Descuento total debe ser suma de descuentos individuales");
        
        DetalleCompra det1 = compra.getDetallesCompra().get(0);
        // Subtotal = (100.0 * 5.0) - 10.0 = 490.0
        assertEquals(490.0, det1.getSubtotal(), 0.001);
        
        DetalleCompra det2 = compra.getDetallesCompra().get(1);
        // Subtotal = (50.0 * 2.0) - 5.0 = 95.0
        assertEquals(95.0, det2.getSubtotal(), 0.001);
    }

    @Test
    @DisplayName("Debe asignar IDs secuenciales a los detalles")
    void agregarDetalles_asignaIDsSecuenciales() {
        // Given
        Compra compra = new Compra();
        
        List<DetalleCompraDTORequest> detalles = List.of(
            new DetalleCompraDTORequest(1, 10.0, 1.0, 0.0, LocalDate.now().plusMonths(6)),
            new DetalleCompraDTORequest(2, 20.0, 1.0, 0.0, LocalDate.now().plusMonths(6)),
            new DetalleCompraDTORequest(3, 30.0, 1.0, 0.0, LocalDate.now().plusMonths(6))
        );
        
        when(detalleCompraDAO.nextId()).thenReturn(100);
        when(productoDAO.findById(anyInt())).thenReturn(productoMock);

        // When
        compraService.agregarDetalles(detalles, compra);

        // Then
        List<DetalleCompra> detallesCompra = compra.getDetallesCompra();
        assertEquals(100, detallesCompra.get(0).getId());
        assertEquals(101, detallesCompra.get(1).getId());
        assertEquals(102, detallesCompra.get(2).getId());
    }

    @Test
    @DisplayName("Debe establecer fecha de vencimiento desde el DTO")
    void agregarDetalles_estableceFechaVencimientoDesdeDTO() {
        // Given
        Compra compra = new Compra();
        LocalDate fechaVencimiento = LocalDate.of(2026, 12, 31);
        
        DetalleCompraDTORequest detalle = new DetalleCompraDTORequest(
            1, 10.0, 1.0, 0.0, fechaVencimiento);
        
        List<DetalleCompraDTORequest> detalles = List.of(detalle);
        
        when(detalleCompraDAO.nextId()).thenReturn(1);
        when(productoDAO.findById(1)).thenReturn(productoMock);

        // When
        compraService.agregarDetalles(detalles, compra);

        // Then
        DetalleCompra detalleCompra = compra.getDetallesCompra().get(0);
        assertEquals(fechaVencimiento, detalleCompra.getFechaVencimiento(),
            "Fecha de vencimiento debe venir del DTO, no calcularse");
    }
}
