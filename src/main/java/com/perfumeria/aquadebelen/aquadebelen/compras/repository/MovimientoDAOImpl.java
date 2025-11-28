package com.perfumeria.aquadebelen.aquadebelen.compras.repository;

import java.time.LocalDateTime;
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

    @Override
    public List<Movimiento> findAll(int page, int size) {
        TypedQuery<Movimiento> query = entityManager.createQuery(
                "SELECT m FROM Movimiento m ORDER BY m.fecha DESC", Movimiento.class);
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        return query.getResultList();
    }

    @Override
    public List<Movimiento> findByFilters(String tipo, LocalDateTime fechaInicio, LocalDateTime fechaFin, Integer subloteId) {
        StringBuilder jpql = new StringBuilder("SELECT m FROM Movimiento m WHERE 1=1");
        
        if (tipo != null && !tipo.trim().isEmpty()) {
            jpql.append(" AND m.referenciaTipo = :tipo");
        }
        if (fechaInicio != null) {
            jpql.append(" AND m.fecha >= :fechaInicio");
        }
        if (fechaFin != null) {
            jpql.append(" AND m.fecha <= :fechaFin");
        }
        if (subloteId != null) {
            jpql.append(" AND m.referenciaId = :subloteId");
        }
        
        jpql.append(" ORDER BY m.fecha DESC");
        
        TypedQuery<Movimiento> query = entityManager.createQuery(jpql.toString(), Movimiento.class);
        
        if (tipo != null && !tipo.trim().isEmpty()) {
            query.setParameter("tipo", tipo);
        }
        if (fechaInicio != null) {
            query.setParameter("fechaInicio", fechaInicio);
        }
        if (fechaFin != null) {
            query.setParameter("fechaFin", fechaFin);
        }
        if (subloteId != null) {
            query.setParameter("subloteId", subloteId);
        }
        
        return query.getResultList();
    }
}
