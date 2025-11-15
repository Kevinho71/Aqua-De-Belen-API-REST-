package com.perfumeria.aquadebelen.aquadebelen.compras.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.perfumeria.aquadebelen.aquadebelen.compras.model.PrecioHistorico;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Repository
public class PrecioHistoricoDAOImpl implements PrecioHistoricoDAO {

    private EntityManager entityManager;

    public PrecioHistoricoDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    @Override
    public void save(PrecioHistorico precioHistorico) {
        entityManager.persist(precioHistorico);
    }

    @Override
    public PrecioHistorico findUltimoPrecioByProductoId(Integer id) {
        TypedQuery<PrecioHistorico> query = entityManager.createQuery(
                "SELECT ph FROM PrecioHistorico ph WHERE ph.producto.id = :productoId ORDER BY ph.fecha DESC",
                PrecioHistorico.class);
        query.setParameter("productoId", id);
        query.setMaxResults(1);
        return query.getSingleResult();
    }

    @Override
    public Integer nextId() {
        TypedQuery<Integer> query = entityManager.createQuery(
                "SELECT COALESCE(MAX(ph.id), 0) + 1 FROM PrecioHistorico ph", Integer.class);
        return query.getSingleResult();
    }

    @Override
    public List<PrecioHistorico> findByProductoId(Integer productoId) {
        TypedQuery<PrecioHistorico> query = entityManager.createQuery(
                "SELECT ph FROM PrecioHistorico ph WHERE ph.producto.id = :productoId ORDER BY ph.fecha DESC",
                PrecioHistorico.class);
        query.setParameter("productoId", productoId);
        return query.getResultList();
    }

    @Override
    public List<PrecioHistorico> findAll() {
        TypedQuery<PrecioHistorico> query = entityManager.createQuery(
                "SELECT ph FROM PrecioHistorico ph ORDER BY ph.fecha DESC",
                PrecioHistorico.class);
        return query.getResultList();
    }

}
