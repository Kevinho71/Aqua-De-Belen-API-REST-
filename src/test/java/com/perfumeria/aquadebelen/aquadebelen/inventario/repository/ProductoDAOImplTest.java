package com.perfumeria.aquadebelen.aquadebelen.inventario.repository;

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

import com.perfumeria.aquadebelen.aquadebelen.inventario.model.Producto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProductoDAOImpl - Tests de búsqueda con filtros")
class ProductoDAOImplTest {

    @Mock
    private EntityManager entityManager;
    
    @Mock
    private TypedQuery<Producto> typedQuery;
    
    @InjectMocks
    private ProductoDAOImpl productoDAO;

    private List<Producto> productosMock;

    @BeforeEach
    void setUp() {
        productosMock = new ArrayList<>();
        Producto p1 = new Producto();
        p1.setNombre("Shampoo Dove");
        productosMock.add(p1);
    }

    @Test
    @DisplayName("Debe buscar por nombre únicamente (case-insensitive)")
    void findByFiltros_soloNombre_aplicaFiltroLike() {
        // Given
        String nombre = "shampoo";
        Integer tipoProductoId = null;
        
        when(entityManager.createQuery(anyString(), eq(Producto.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(eq("nombre"), anyString())).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(productosMock);

        // When
        List<Producto> result = productoDAO.findByFiltros(nombre, tipoProductoId);

        // Then
        assertNotNull(result);
        verify(entityManager).createQuery(
            argThat(query -> query.contains("LOWER(p.nombre) LIKE LOWER(:nombre)")),
            eq(Producto.class)
        );
        verify(typedQuery).setParameter("nombre", "%shampoo%");
        verify(typedQuery, never()).setParameter(eq("tipoProductoId"), anyInt());
    }

    @Test
    @DisplayName("Debe buscar por tipo de producto únicamente")
    void findByFiltros_soloTipoProducto_aplicaFiltroEquals() {
        // Given
        String nombre = null;
        Integer tipoProductoId = 5;
        
        when(entityManager.createQuery(anyString(), eq(Producto.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(eq("tipoProductoId"), anyInt())).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(productosMock);

        // When
        List<Producto> result = productoDAO.findByFiltros(nombre, tipoProductoId);

        // Then
        assertNotNull(result);
        verify(entityManager).createQuery(
            argThat(query -> query.contains("p.tipoProducto.id = :tipoProductoId")),
            eq(Producto.class)
        );
        verify(typedQuery).setParameter("tipoProductoId", 5);
        verify(typedQuery, never()).setParameter(eq("nombre"), anyString());
    }

    @Test
    @DisplayName("Debe buscar combinando nombre y tipo de producto")
    void findByFiltros_nombreYTipo_aplicaAmbosFiltros() {
        // Given
        String nombre = "dove";
        Integer tipoProductoId = 3;
        
        when(entityManager.createQuery(anyString(), eq(Producto.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(anyString(), any())).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(productosMock);

        // When
        List<Producto> result = productoDAO.findByFiltros(nombre, tipoProductoId);

        // Then
        assertNotNull(result);
        verify(entityManager).createQuery(
            argThat(query -> 
                query.contains("LOWER(p.nombre) LIKE LOWER(:nombre)") &&
                query.contains("p.tipoProducto.id = :tipoProductoId")
            ),
            eq(Producto.class)
        );
        verify(typedQuery).setParameter("nombre", "%dove%");
        verify(typedQuery).setParameter("tipoProductoId", 3);
    }

    @Test
    @DisplayName("Debe retornar todos los productos cuando no hay filtros")
    void findByFiltros_sinFiltros_retornaTodos() {
        // Given
        String nombre = null;
        Integer tipoProductoId = null;
        
        when(entityManager.createQuery(anyString(), eq(Producto.class))).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(productosMock);

        // When
        List<Producto> result = productoDAO.findByFiltros(nombre, tipoProductoId);

        // Then
        assertNotNull(result);
        verify(entityManager).createQuery(
            argThat(query -> query.equals("SELECT p FROM Producto p WHERE 1=1")),
            eq(Producto.class)
        );
        verify(typedQuery, never()).setParameter(anyString(), any());
    }

    @Test
    @DisplayName("Debe ignorar nombre vacío o con solo espacios")
    void findByFiltros_nombreVacio_noAplicaFiltro() {
        // Given
        String nombre = "   ";  // Solo espacios
        Integer tipoProductoId = null;
        
        when(entityManager.createQuery(anyString(), eq(Producto.class))).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(productosMock);

        // When
        productoDAO.findByFiltros(nombre, tipoProductoId);

        // Then
        verify(entityManager).createQuery(
            argThat(query -> !query.contains("LOWER(p.nombre)")),
            eq(Producto.class)
        );
        verify(typedQuery, never()).setParameter(eq("nombre"), anyString());
    }

    @Test
    @DisplayName("Debe agregar wildcards (%) al nombre para búsqueda parcial")
    void findByFiltros_nombre_agregaWildcards() {
        // Given
        String nombre = "dior";
        Integer tipoProductoId = null;
        
        when(entityManager.createQuery(anyString(), eq(Producto.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(anyString(), any())).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(productosMock);

        // When
        productoDAO.findByFiltros(nombre, tipoProductoId);

        // Then
        verify(typedQuery).setParameter("nombre", "%dior%");
    }
}
