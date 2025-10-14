package com.perfumeria.aquadebelen.aquadebelen.inventario.repository;

import org.springframework.stereotype.Repository;

import com.perfumeria.aquadebelen.aquadebelen.inventario.model.Producto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Repository
public class ProductoDAOImpl implements ProductoDAO{
    private EntityManager entityManager;

    public ProductoDAOImpl(EntityManager entityManager){
        this.entityManager=entityManager;
    }

    @Override
    public Producto findById(Integer id) {
        TypedQuery<Producto> query = entityManager.createQuery(("SELECT p FROM Producto p WHERE p.id = :data"), Producto.class);
        query.setParameter("data", id);
        return query.getSingleResult();
    }

    @Transactional
    @Override
    public void store(Producto producto) {
        entityManager.persist(producto);
    }
}
