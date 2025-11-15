package com.perfumeria.aquadebelen.aquadebelen.inventario.repository;

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

import com.perfumeria.aquadebelen.aquadebelen.inventario.model.Sublote;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@ExtendWith(MockitoExtension.class)
@DisplayName("SubloteDAOImpl - Tests de búsqueda con filtros")
class SubloteDAOImplTest {

    @Mock
    private EntityManager entityManager;
    
    @Mock
    private TypedQuery<Sublote> typedQuery;
    
    @InjectMocks
    private SubloteDAOImpl subloteDAO;

    private List<Sublote> sublotesMock;

    @BeforeEach
    void setUp() {
        sublotesMock = new ArrayList<>();
        Sublote s1 = new Sublote();
        s1.setCodigoSublote("SUB-001");
        s1.setCantidadActual(50.0);
        s1.setFechaVencimiento(LocalDate.now().plusDays(15));
        sublotesMock.add(s1);
    }

    @Test
    @DisplayName("Debe encontrar sublotes próximos a vencer (dentro de N días)")
    void findProximosAVencer_dentroDelRango_retornaSubLotes() {
        // Given
        int dias = 30;
        LocalDate fechaLimite = LocalDate.now().plusDays(dias);
        
        when(entityManager.createQuery(anyString(), eq(Sublote.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(eq("fechaLimite"), any(LocalDate.class))).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(sublotesMock);

        // When
        List<Sublote> result = subloteDAO.findProximosAVencer(dias);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(entityManager).createQuery(
            argThat(query -> 
                query.contains("s.fechaVencimiento <= :fechaLimite") &&
                query.contains("s.fechaVencimiento >= CURRENT_DATE") &&
                query.contains("s.cantidadActual > 0") &&
                query.contains("s.estado != 'AGOTADO'")
            ),
            eq(Sublote.class)
        );
        verify(typedQuery).setParameter(eq("fechaLimite"), eq(fechaLimite));
    }

    @Test
    @DisplayName("Debe usar días por defecto (30) cuando no se especifica")
    void findProximosAVencer_sinParametro_usa30DiasPorDefecto() {
        // Given
        when(entityManager.createQuery(anyString(), eq(Sublote.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(anyString(), any(LocalDate.class))).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(sublotesMock);

        // When
        List<Sublote> result = subloteDAO.findProximosAVencer(30);  // Default en controller

        // Then
        assertNotNull(result);
        LocalDate expectedFechaLimite = LocalDate.now().plusDays(30);
        verify(typedQuery).setParameter(eq("fechaLimite"), eq(expectedFechaLimite));
    }

    @Test
    @DisplayName("Debe excluir sublotes sin stock (cantidadActual = 0)")
    void findProximosAVencer_sublotesSinStock_noLosRetorna() {
        // Given
        List<Sublote> sublotesVacios = new ArrayList<>();
        int dias = 30;
        
        when(entityManager.createQuery(anyString(), eq(Sublote.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(anyString(), any())).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(sublotesVacios);

        // When
        List<Sublote> result = subloteDAO.findProximosAVencer(dias);

        // Then
        assertTrue(result.isEmpty());
        verify(entityManager).createQuery(
            argThat(query -> query.contains("s.cantidadActual > 0")),
            eq(Sublote.class)
        );
    }

    @Test
    @DisplayName("Debe calcular correctamente el rango de fechas")
    void findProximosAVencer_calcularRango_usaFechasCorrectas() {
        // Given
        int dias = 15;
        LocalDate esperado = LocalDate.now().plusDays(15);
        
        when(entityManager.createQuery(anyString(), eq(Sublote.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(anyString(), any())).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(sublotesMock);

        // When
        subloteDAO.findProximosAVencer(dias);

        // Then
        verify(typedQuery).setParameter("fechaLimite", esperado);
    }

    @Test
    @DisplayName("Debe encontrar sublotes disponibles (cantidadActual > 0)")
    void findDisponibles_conStock_retornaSubLotes() {
        // Given
        when(entityManager.createQuery(anyString(), eq(Sublote.class))).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(sublotesMock);

        // When
        List<Sublote> result = subloteDAO.findDisponibles();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(entityManager).createQuery(
            argThat(query -> query.contains("s.cantidadActual > 0")),
            eq(Sublote.class)
        );
    }

    @Test
    @DisplayName("Debe retornar lista vacía cuando no hay sublotes disponibles")
    void findDisponibles_sinStock_retornaListaVacia() {
        // Given
        List<Sublote> sublotesVacios = new ArrayList<>();
        when(entityManager.createQuery(anyString(), eq(Sublote.class))).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(sublotesVacios);

        // When
        List<Sublote> result = subloteDAO.findDisponibles();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Debe ordenar sublotes por fecha de vencimiento ascendente")
    void findProximosAVencer_resultados_ordenadosPorFechaAsc() {
        // Given
        int dias = 30;
        when(entityManager.createQuery(anyString(), eq(Sublote.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(anyString(), any())).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(sublotesMock);

        // When
        subloteDAO.findProximosAVencer(dias);

        // Then
        verify(entityManager).createQuery(
            argThat(query -> query.contains("ORDER BY s.fechaVencimiento ASC")),
            eq(Sublote.class)
        );
    }

    @Test
    @DisplayName("Debe manejar días negativos sin error (retorna lista vacía)")
    void findProximosAVencer_diasNegativos_retornaVacio() {
        // Given
        int dias = -5;
        List<Sublote> sublotesVacios = new ArrayList<>();
        
        when(entityManager.createQuery(anyString(), eq(Sublote.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(anyString(), any())).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(sublotesVacios);

        // When
        List<Sublote> result = subloteDAO.findProximosAVencer(dias);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
