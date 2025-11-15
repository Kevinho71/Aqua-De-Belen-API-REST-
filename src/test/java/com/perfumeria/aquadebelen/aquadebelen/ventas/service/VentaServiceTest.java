package com.perfumeria.aquadebelen.aquadebelen.ventas.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.perfumeria.aquadebelen.aquadebelen.clientes.repository.ClienteDAO;
import com.perfumeria.aquadebelen.aquadebelen.compras.model.PrecioHistorico;
import com.perfumeria.aquadebelen.aquadebelen.compras.repository.PrecioHistoricoDAO;
import com.perfumeria.aquadebelen.aquadebelen.compras.service.MovimientoService;
import com.perfumeria.aquadebelen.aquadebelen.inventario.model.Producto;
import com.perfumeria.aquadebelen.aquadebelen.inventario.model.Sublote;
import com.perfumeria.aquadebelen.aquadebelen.inventario.repository.ProductoDAO;
import com.perfumeria.aquadebelen.aquadebelen.inventario.service.SubloteService;
import com.perfumeria.aquadebelen.aquadebelen.ventas.DTO.DetalleVentaRequest;
import com.perfumeria.aquadebelen.aquadebelen.ventas.model.Venta;
import com.perfumeria.aquadebelen.aquadebelen.ventas.repository.DetalleVentaDAO;
import com.perfumeria.aquadebelen.aquadebelen.ventas.repository.MetodoDePagoDAO;
import com.perfumeria.aquadebelen.aquadebelen.ventas.repository.VentaDAO;

@ExtendWith(MockitoExtension.class)
@DisplayName("VentaService - Tests Unitarios")
class VentaServiceTest {

    @Mock
    private VentaDAO ventaDAO;
    
    @Mock
    private MetodoDePagoDAO metodoDePagoDAO;
    
    @Mock
    private ClienteDAO clienteDAO;
    
    @Mock
    private ProductoDAO productoDAO;
    
    @Mock
    private PrecioHistoricoDAO precioHistoricoDAO;
    
    @Mock
    private DetalleVentaDAO detalleVentaDAO;
    
    @Mock
    private SubloteService subloteService;
    
    @Mock
    private MovimientoService movimientoService;
    
    @InjectMocks
    private VentaService ventaService;

    private Producto productoMock;
    private PrecioHistorico precioMock;
    private Sublote subloteMock;

    @BeforeEach
    void setUp() {
        productoMock = new Producto();
        productoMock.setId(1);
        productoMock.setNombre("Shampoo Dove");
        
        precioMock = new PrecioHistorico();
        precioMock.setPrecioVenta(25.50);
        
        subloteMock = new Sublote();
        subloteMock.setId(1);
        subloteMock.setCantidadActual(100.0);
    }

    @Test
    @DisplayName("Debe calcular total bruto correctamente con un producto")
    void calcularTotalBruto_unProducto_retornaSumaCorrecta() {
        // Given
        List<DetalleVentaRequest> detalles = new ArrayList<>();
        detalles.add(new DetalleVentaRequest(1, 2.0, 0.0)); // 2 unidades a 25.50 = 51.00
        
        when(productoDAO.findById(1)).thenReturn(productoMock);
        when(precioHistoricoDAO.findUltimoPrecioByProductoId(1)).thenReturn(precioMock);

        // When
        double resultado = ventaService.calcularTotalBruto(detalles);

        // Then
        assertEquals(51.00, resultado, 0.01);
        verify(productoDAO).findById(1);
        verify(precioHistoricoDAO).findUltimoPrecioByProductoId(1);
    }

    @Test
    @DisplayName("Debe calcular total bruto con múltiples productos")
    void calcularTotalBruto_multipleProductos_retornaSumaTotal() {
        // Given
        Producto producto2 = new Producto();
        producto2.setId(2);
        PrecioHistorico precio2 = new PrecioHistorico();
        precio2.setPrecioVenta(15.00);
        
        List<DetalleVentaRequest> detalles = new ArrayList<>();
        detalles.add(new DetalleVentaRequest(1, 2.0, 0.0)); // 2 * 25.50 = 51.00
        detalles.add(new DetalleVentaRequest(2, 3.0, 0.0)); // 3 * 15.00 = 45.00
        // Total esperado: 96.00
        
        when(productoDAO.findById(1)).thenReturn(productoMock);
        when(productoDAO.findById(2)).thenReturn(producto2);
        when(precioHistoricoDAO.findUltimoPrecioByProductoId(1)).thenReturn(precioMock);
        when(precioHistoricoDAO.findUltimoPrecioByProductoId(2)).thenReturn(precio2);

        // When
        double resultado = ventaService.calcularTotalBruto(detalles);

        // Then
        assertEquals(96.00, resultado, 0.01);
    }

    @Test
    @DisplayName("Debe retornar 0 cuando la lista de detalles está vacía")
    void calcularTotalBruto_listaVacia_retornaCero() {
        // Given
        List<DetalleVentaRequest> detalles = new ArrayList<>();

        // When
        double resultado = ventaService.calcularTotalBruto(detalles);

        // Then
        assertEquals(0.0, resultado);
        verify(productoDAO, never()).findById(anyInt());
    }

    @Test
    @DisplayName("Debe calcular subtotal restando descuento en agregarDetalles")
    void agregarDetalles_conDescuento_calculaSubtotalCorrecto() {
        // Given
        List<DetalleVentaRequest> detalles = new ArrayList<>();
        detalles.add(new DetalleVentaRequest(1, 2.0, 5.0)); // 2 * 25.50 - 5.0 = 46.00
        
        Venta venta = new Venta();
        venta.setId(1);
        
        when(detalleVentaDAO.nextId()).thenReturn(1);
        when(productoDAO.findById(1)).thenReturn(productoMock);
        when(precioHistoricoDAO.findUltimoPrecioByProductoId(1)).thenReturn(precioMock);
        when(subloteService.buscarProximoAVencer(1)).thenReturn(subloteMock);

        // When
        ventaService.agregarDetalles(detalles, venta);

        // Then
        assertEquals(1, venta.getDetallesVentas().size());
        assertEquals(46.00, venta.getDetallesVentas().get(0).getSubtotal(), 0.01);
        assertEquals(5.0, venta.getDescuentoTotal(), 0.01);
        verify(subloteService).descontarCantidad(subloteMock, 2.0);
    }

    @Test
    @DisplayName("Debe descontar stock del sublote más próximo a vencer")
    void agregarDetalles_descuentaStockDelSublote() {
        // Given
        List<DetalleVentaRequest> detalles = new ArrayList<>();
        detalles.add(new DetalleVentaRequest(1, 5.0, 0.0));
        
        Venta venta = new Venta();
        venta.setId(1);
        
        when(detalleVentaDAO.nextId()).thenReturn(1);
        when(productoDAO.findById(1)).thenReturn(productoMock);
        when(precioHistoricoDAO.findUltimoPrecioByProductoId(1)).thenReturn(precioMock);
        when(subloteService.buscarProximoAVencer(1)).thenReturn(subloteMock);

        // When
        ventaService.agregarDetalles(detalles, venta);

        // Then
        verify(subloteService).buscarProximoAVencer(1);
        verify(subloteService).descontarCantidad(subloteMock, 5.0);
    }

    @Test
    @DisplayName("Debe asignar IDs secuenciales a múltiples detalles")
    void agregarDetalles_multipleDetalles_asignaIDsSecuenciales() {
        // Given
        List<DetalleVentaRequest> detalles = new ArrayList<>();
        detalles.add(new DetalleVentaRequest(1, 2.0, 0.0));
        detalles.add(new DetalleVentaRequest(1, 3.0, 0.0));
        detalles.add(new DetalleVentaRequest(1, 1.0, 0.0));
        
        Venta venta = new Venta();
        venta.setId(1);
        
        when(detalleVentaDAO.nextId()).thenReturn(10);
        when(productoDAO.findById(1)).thenReturn(productoMock);
        when(precioHistoricoDAO.findUltimoPrecioByProductoId(1)).thenReturn(precioMock);
        when(subloteService.buscarProximoAVencer(1)).thenReturn(subloteMock);

        // When
        ventaService.agregarDetalles(detalles, venta);

        // Then
        assertEquals(3, venta.getDetallesVentas().size());
        assertEquals(10, venta.getDetallesVentas().get(0).getId());
        assertEquals(11, venta.getDetallesVentas().get(1).getId());
        assertEquals(12, venta.getDetallesVentas().get(2).getId());
    }

    @Test
    @DisplayName("Debe acumular descuentos correctamente en descuentoTotal")
    void agregarDetalles_multipleDescuentos_acumulaCorrectamente() {
        // Given
        List<DetalleVentaRequest> detalles = new ArrayList<>();
        detalles.add(new DetalleVentaRequest(1, 1.0, 5.0));
        detalles.add(new DetalleVentaRequest(1, 1.0, 3.0));
        detalles.add(new DetalleVentaRequest(1, 1.0, 2.0));
        // Total descuentos: 5 + 3 + 2 = 10.0
        
        Venta venta = new Venta();
        venta.setId(1);
        
        when(detalleVentaDAO.nextId()).thenReturn(1);
        when(productoDAO.findById(1)).thenReturn(productoMock);
        when(precioHistoricoDAO.findUltimoPrecioByProductoId(1)).thenReturn(precioMock);
        when(subloteService.buscarProximoAVencer(1)).thenReturn(subloteMock);

        // When
        ventaService.agregarDetalles(detalles, venta);

        // Then
        assertEquals(10.0, venta.getDescuentoTotal(), 0.01);
    }

    @Test
    @DisplayName("Debe usar el precio de venta del último precio histórico")
    void agregarDetalles_usaUltimoPrecioHistorico() {
        // Given
        List<DetalleVentaRequest> detalles = new ArrayList<>();
        detalles.add(new DetalleVentaRequest(1, 1.0, 0.0));
        
        Venta venta = new Venta();
        venta.setId(1);
        
        when(detalleVentaDAO.nextId()).thenReturn(1);
        when(productoDAO.findById(1)).thenReturn(productoMock);
        when(precioHistoricoDAO.findUltimoPrecioByProductoId(1)).thenReturn(precioMock);
        when(subloteService.buscarProximoAVencer(1)).thenReturn(subloteMock);

        // When
        ventaService.agregarDetalles(detalles, venta);

        // Then
        verify(precioHistoricoDAO, times(1)).findUltimoPrecioByProductoId(1);
        assertEquals(25.50, venta.getDetallesVentas().get(0).getSubtotal(), 0.01);
    }
}
