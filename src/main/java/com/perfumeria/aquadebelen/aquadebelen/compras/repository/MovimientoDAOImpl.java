package com.perfumeria.aquadebelen.aquadebelen.compras.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.perfumeria.aquadebelen.aquadebelen.compras.model.Movimiento;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Repository
public class MovimientoDAOImpl implements MovimientoDAO {

    @Autowired
    private EntityManager entityManager;

    @Transactional
    @Override
    public void store(Movimiento movimiento) {
        if (movimiento.getId() == null) {
            entityManager.persist(movimiento);
        } else {
            entityManager.merge(movimiento);
        }
    }

    @Override
    public Integer nextId() {
        TypedQuery<Integer> query = entityManager.createQuery(
                "SELECT COALESCE(MAX(m.id), 0) + 1 FROM Movimiento m", Integer.class);
        return query.getSingleResult();
    }
}
