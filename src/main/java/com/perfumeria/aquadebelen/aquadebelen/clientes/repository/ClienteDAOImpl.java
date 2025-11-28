package com.perfumeria.aquadebelen.aquadebelen.clientes.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.perfumeria.aquadebelen.aquadebelen.clientes.model.Cliente;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;


@Repository
public class ClienteDAOImpl implements ClienteDAO {

    private EntityManager entityManager;

    public ClienteDAOImpl(EntityManager entityManager){
        this.entityManager=entityManager;
    }

    @Transactional
    @Override
    public void store(Cliente cliente) {
        if (cliente.getId() == null || entityManager.find(Cliente.class, cliente.getId()) == null) {
            entityManager.persist(cliente);
        } else {
            entityManager.merge(cliente);
        }
        entityManager.flush();
    }

    @Override
    public Cliente findById(Integer id) {
        TypedQuery<Cliente> query = entityManager.createQuery(
            "SELECT c FROM Cliente c WHERE c.id = :id", Cliente.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public List<Cliente> findAll() {
        TypedQuery<Cliente> query = entityManager.createQuery(
            "SELECT c FROM Cliente c", Cliente.class);
        return query.getResultList();
    }

    @Override
    public List<Cliente> findAll(int page, int size) {
        TypedQuery<Cliente> query = entityManager.createQuery(
            "SELECT c FROM Cliente c", Cliente.class);
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        return query.getResultList();
    }

    @Transactional
    @Override
    public void deleteById(Integer id) {
        entityManager.createQuery("DELETE FROM Cliente c WHERE c.id = :id")
            .setParameter("id", id)
            .executeUpdate();
    }

    @Override
    public Integer nextId() {
        TypedQuery<Integer> query = entityManager.createQuery(
            "SELECT COALESCE(MAX(c.id), 0) FROM Cliente c", Integer.class);
        return query.getSingleResult() + 1;
    }

    @Override
    public List<Cliente> findByFiltros(String nombre, String apellido, String nitCi) {
        StringBuilder jpql = new StringBuilder("SELECT c FROM Cliente c WHERE 1=1");
        
        if (nombre != null && !nombre.trim().isEmpty()) {
            jpql.append(" AND LOWER(c.nombre) LIKE LOWER(:nombre)");
        }
        
        if (apellido != null && !apellido.trim().isEmpty()) {
            jpql.append(" AND LOWER(c.apellido) LIKE LOWER(:apellido)");
        }
        
        if (nitCi != null && !nitCi.trim().isEmpty()) {
            jpql.append(" AND c.nitCi = :nitCi");
        }
        
        TypedQuery<Cliente> query = entityManager.createQuery(jpql.toString(), Cliente.class);
        
        if (nombre != null && !nombre.trim().isEmpty()) {
            query.setParameter("nombre", "%" + nombre + "%");
        }
        
        if (apellido != null && !apellido.trim().isEmpty()) {
            query.setParameter("apellido", "%" + apellido + "%");
        }
        
        if (nitCi != null && !nitCi.trim().isEmpty()) {
            query.setParameter("nitCi", nitCi);
        }
        
        return query.getResultList();
    }
}
