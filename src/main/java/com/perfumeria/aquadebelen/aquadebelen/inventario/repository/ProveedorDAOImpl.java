package com.perfumeria.aquadebelen.aquadebelen.inventario.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.perfumeria.aquadebelen.aquadebelen.inventario.model.Proveedor;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Repository
public class ProveedorDAOImpl implements ProveedorDAO {

    private EntityManager entityManager;

    public ProveedorDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    @Override
    public void store(Proveedor proveedor) {
        entityManager.merge(proveedor);
        entityManager.flush();
    }

    @Override
    public Proveedor findById(Integer id) {
        TypedQuery<Proveedor> query = entityManager.createQuery("SELECT p FROM Proveedor p WHERE p.id = :data",
                Proveedor.class);
        query.setParameter("data", id);
        return query.getSingleResult();
    }

    @Transactional
    @Override
    public void deleteById(Integer id) {
        entityManager.createQuery("DELETE FROM Proveedor p WHERE p.id = :data").setParameter("data", id)
                .executeUpdate();
    }

    @Override
    public List<Proveedor> list() {
        TypedQuery<Proveedor> query = entityManager.createQuery("SELECT p FROM Proveedor p", Proveedor.class);
        return query.getResultList();
    }

    @Override
    public Integer nextId() {
        TypedQuery<Integer> query = entityManager.createQuery("SELECT COALESCE(MAX(p.id), 0) FROM Proveedor p",
                Integer.class);
        return query.getSingleResult() + 1;
    }

    @Override
    public List<Proveedor> findByFilters(String nombre, String nit) {
        StringBuilder jpql = new StringBuilder("SELECT p FROM Proveedor p WHERE 1=1");
        
        if (nombre != null && !nombre.trim().isEmpty()) {
            jpql.append(" AND LOWER(p.nombre) LIKE LOWER(:nombre)");
        }
        if (nit != null && !nit.trim().isEmpty()) {
            jpql.append(" AND p.nit LIKE :nit");
        }
        
        jpql.append(" ORDER BY p.nombre ASC");
        
        TypedQuery<Proveedor> query = entityManager.createQuery(jpql.toString(), Proveedor.class);
        
        if (nombre != null && !nombre.trim().isEmpty()) {
            query.setParameter("nombre", "%" + nombre + "%");
        }
        if (nit != null && !nit.trim().isEmpty()) {
            query.setParameter("nit", "%" + nit + "%");
        }
        
        return query.getResultList();
    }

}
