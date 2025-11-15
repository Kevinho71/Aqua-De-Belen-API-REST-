package com.perfumeria.aquadebelen.aquadebelen.compras.repository;

import org.springframework.stereotype.Repository;

import com.perfumeria.aquadebelen.aquadebelen.compras.model.DetalleCompra;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class DetalleCompraDAOImpl implements DetalleCompraDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Integer nextId() {
        Integer maxId = em.createQuery("SELECT COALESCE(MAX(dc.id), 0) FROM DetalleCompra dc", Integer.class)
                .getSingleResult();
        return maxId + 1;
    }

    @Override
    public DetalleCompra store(DetalleCompra detalleCompra) {
        if (detalleCompra.getId() == null) {
            em.persist(detalleCompra);
            return detalleCompra;
        } else {
            return em.merge(detalleCompra);
        }
    }
}
