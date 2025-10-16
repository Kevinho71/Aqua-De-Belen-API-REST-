package com.perfumeria.aquadebelen.aquadebelen.inventario.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.perfumeria.aquadebelen.aquadebelen.inventario.model.Proveedor;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Repository
public class ProveedorDAOImpl implements ProveedorDAO {

    private EntityManager entityManager;

    public ProveedorDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

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
        TypedQuery<Integer> query = entityManager.createQuery("SELECT COALESCE(MAX(p.id), 0) FROM Producto p ",
                Integer.class);
        return query.getSingleResult() + 1;
    }

}
