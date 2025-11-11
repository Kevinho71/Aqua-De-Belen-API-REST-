package com.perfumeria.aquadebelen.aquadebelen.compras.repository;

import java.util.List;

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

    @Override
    public Movimiento findById(Integer id) {
        TypedQuery<Movimiento> query = entityManager.createQuery(
                "SELECT m FROM Movimiento m WHERE m.id = :id", Movimiento.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public List<Movimiento> findAll() {
        TypedQuery<Movimiento> query = entityManager.createQuery(
                "SELECT m FROM Movimiento m ORDER BY m.fecha DESC", Movimiento.class);
        return query.getResultList();
    }
}
