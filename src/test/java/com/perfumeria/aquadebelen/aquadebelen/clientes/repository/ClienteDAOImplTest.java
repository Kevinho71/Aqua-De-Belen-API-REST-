package com.perfumeria.aquadebelen.aquadebelen.clientes.repository;

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

import com.perfumeria.aquadebelen.aquadebelen.clientes.model.Cliente;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@ExtendWith(MockitoExtension.class)
@DisplayName("ClienteDAOImpl - Tests de búsqueda con filtros")
class ClienteDAOImplTest {

    @Mock
    private EntityManager entityManager;
    
    @Mock
    private TypedQuery<Cliente> typedQuery;
    
    @InjectMocks
    private ClienteDAOImpl clienteDAO;

    private List<Cliente> clientesMock;

    @BeforeEach
    void setUp() {
        clientesMock = new ArrayList<>();
        Cliente c1 = new Cliente();
        c1.setNombre("Juan");
        c1.setApellido("Pérez");
        c1.setNitCi("12345678");
        clientesMock.add(c1);
    }

    @Test
    @DisplayName("Debe buscar por nombre únicamente (case-insensitive)")
    void findByFiltros_soloNombre_aplicaFiltroLike() {
        // Given
        String nombre = "juan";
        String apellido = null;
        String nitCi = null;
        
        when(entityManager.createQuery(anyString(), eq(Cliente.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(eq("nombre"), anyString())).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(clientesMock);

        // When
        List<Cliente> result = clienteDAO.findByFiltros(nombre, apellido, nitCi);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(entityManager).createQuery(
            argThat(query -> query.contains("LOWER(c.nombre) LIKE LOWER(:nombre)")),
            eq(Cliente.class)
        );
        verify(typedQuery).setParameter("nombre", "%juan%");
        verify(typedQuery, never()).setParameter(eq("apellido"), anyString());
        verify(typedQuery, never()).setParameter(eq("nitCi"), anyString());
    }

    @Test
    @DisplayName("Debe buscar por apellido únicamente")
    void findByFiltros_soloApellido_aplicaFiltroLike() {
        // Given
        String nombre = null;
        String apellido = "perez";
        String nitCi = null;
        
        when(entityManager.createQuery(anyString(), eq(Cliente.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(eq("apellido"), anyString())).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(clientesMock);

        // When
        List<Cliente> result = clienteDAO.findByFiltros(nombre, apellido, nitCi);

        // Then
        assertNotNull(result);
        verify(entityManager).createQuery(
            argThat(query -> query.contains("LOWER(c.apellido) LIKE LOWER(:apellido)")),
            eq(Cliente.class)
        );
        verify(typedQuery).setParameter("apellido", "%perez%");
        verify(typedQuery, never()).setParameter(eq("nombre"), anyString());
        verify(typedQuery, never()).setParameter(eq("nitCi"), anyString());
    }

    @Test
    @DisplayName("Debe buscar por NIT/CI únicamente (búsqueda exacta)")
    void findByFiltros_soloNitCi_aplicaFiltroEquals() {
        // Given
        String nombre = null;
        String apellido = null;
        String nitCi = "12345678";
        
        when(entityManager.createQuery(anyString(), eq(Cliente.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(eq("nitCi"), anyString())).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(clientesMock);

        // When
        List<Cliente> result = clienteDAO.findByFiltros(nombre, apellido, nitCi);

        // Then
        assertNotNull(result);
        verify(entityManager).createQuery(
            argThat(query -> query.contains("c.nitCi = :nitCi")),
            eq(Cliente.class)
        );
        verify(typedQuery).setParameter("nitCi", "12345678");
        verify(typedQuery, never()).setParameter(eq("nombre"), anyString());
        verify(typedQuery, never()).setParameter(eq("apellido"), anyString());
    }

    @Test
    @DisplayName("Debe buscar combinando nombre y apellido")
    void findByFiltros_nombreYApellido_aplicaAmbosFiltros() {
        // Given
        String nombre = "juan";
        String apellido = "perez";
        String nitCi = null;
        
        when(entityManager.createQuery(anyString(), eq(Cliente.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(anyString(), any())).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(clientesMock);

        // When
        List<Cliente> result = clienteDAO.findByFiltros(nombre, apellido, nitCi);

        // Then
        assertNotNull(result);
        verify(entityManager).createQuery(
            argThat(query -> 
                query.contains("LOWER(c.nombre) LIKE LOWER(:nombre)") &&
                query.contains("LOWER(c.apellido) LIKE LOWER(:apellido)")
            ),
            eq(Cliente.class)
        );
        verify(typedQuery).setParameter("nombre", "%juan%");
        verify(typedQuery).setParameter("apellido", "%perez%");
    }

    @Test
    @DisplayName("Debe buscar combinando los tres filtros")
    void findByFiltros_todosLosFiltros_aplicaTresFiltros() {
        // Given
        String nombre = "juan";
        String apellido = "perez";
        String nitCi = "12345678";
        
        when(entityManager.createQuery(anyString(), eq(Cliente.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(anyString(), any())).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(clientesMock);

        // When
        List<Cliente> result = clienteDAO.findByFiltros(nombre, apellido, nitCi);

        // Then
        assertNotNull(result);
        verify(entityManager).createQuery(
            argThat(query -> 
                query.contains("LOWER(c.nombre) LIKE LOWER(:nombre)") &&
                query.contains("LOWER(c.apellido) LIKE LOWER(:apellido)") &&
                query.contains("c.nitCi = :nitCi")
            ),
            eq(Cliente.class)
        );
        verify(typedQuery).setParameter("nombre", "%juan%");
        verify(typedQuery).setParameter("apellido", "%perez%");
        verify(typedQuery).setParameter("nitCi", "12345678");
    }

    @Test
    @DisplayName("Debe retornar todos los clientes cuando no hay filtros")
    void findByFiltros_sinFiltros_retornaTodos() {
        // Given
        String nombre = null;
        String apellido = null;
        String nitCi = null;
        
        when(entityManager.createQuery(anyString(), eq(Cliente.class))).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(clientesMock);

        // When
        List<Cliente> result = clienteDAO.findByFiltros(nombre, apellido, nitCi);

        // Then
        assertNotNull(result);
        verify(entityManager).createQuery(
            argThat(query -> query.equals("SELECT c FROM Cliente c WHERE 1=1")),
            eq(Cliente.class)
        );
        verify(typedQuery, never()).setParameter(anyString(), any());
    }

    @Test
    @DisplayName("Debe ignorar nombre y apellido vacíos")
    void findByFiltros_nombresVacios_soloAplicaNitCi() {
        // Given
        String nombre = "  ";
        String apellido = "";
        String nitCi = "12345678";
        
        when(entityManager.createQuery(anyString(), eq(Cliente.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(eq("nitCi"), anyString())).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(clientesMock);

        // When
        List<Cliente> result = clienteDAO.findByFiltros(nombre, apellido, nitCi);

        // Then
        verify(entityManager).createQuery(
            argThat(query -> 
                !query.contains("nombre") && 
                !query.contains("apellido") &&
                query.contains("c.nitCi = :nitCi")
            ),
            eq(Cliente.class)
        );
        verify(typedQuery).setParameter("nitCi", "12345678");
        verify(typedQuery, never()).setParameter(eq("nombre"), anyString());
        verify(typedQuery, never()).setParameter(eq("apellido"), anyString());
    }

    @Test
    @DisplayName("Debe agregar wildcards (%) para búsqueda parcial en nombre y apellido")
    void findByFiltros_nombreYApellido_agregaWildcards() {
        // Given
        String nombre = "ju";
        String apellido = "per";
        String nitCi = null;
        
        when(entityManager.createQuery(anyString(), eq(Cliente.class))).thenReturn(typedQuery);
        when(typedQuery.setParameter(anyString(), any())).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(clientesMock);

        // When
        clienteDAO.findByFiltros(nombre, apellido, nitCi);

        // Then
        verify(typedQuery).setParameter("nombre", "%ju%");
        verify(typedQuery).setParameter("apellido", "%per%");
    }
}
