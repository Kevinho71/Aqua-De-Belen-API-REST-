package com.perfumeria.aquadebelen.aquadebelen.inventario.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.perfumeria.aquadebelen.aquadebelen.inventario.model.Sublote;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Repository
public class SubloteDAOImpl implements SubloteDAO{

    @Autowired
    private EntityManager entityManager;

    @Override
    public Sublote findById(Integer id) {
        TypedQuery<Sublote> query = entityManager.createQuery("SELECT s FROM Sublote s WHERE s.id = :id", Sublote.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Transactional
    @Override
    public void store(Sublote sublote) {
        if (sublote.getId() == null) {
            entityManager.persist(sublote);
        } else {
            entityManager.merge(sublote);
        }
    }

    @Override
    public List<Sublote> list() {
        TypedQuery<Sublote> query = entityManager.createQuery("SELECT s FROM Sublote s", Sublote.class);
        return query.getResultList();
    }

    @Override
    public Integer nextId() {
        TypedQuery<Integer> query = entityManager.createQuery(
                "SELECT COALESCE(MAX(s.id), 0) + 1 FROM Sublote s", Integer.class);
        return query.getSingleResult();
    }

    @Override
    public Sublote findProximoAVencerByProductoId(Integer productoId) {
        TypedQuery<Sublote> query = entityManager.createQuery(
                "SELECT s FROM Sublote s WHERE s.producto.id = :productoId " +
                "AND s.cantidadActual > 0 AND s.estado != 'AGOTADO' " +
                "ORDER BY s.fechaVencimiento ASC", 
                Sublote.class);
        query.setParameter("productoId", productoId);
        query.setMaxResults(1);
        List<Sublote> resultados = query.getResultList();
        return resultados.isEmpty() ? null : resultados.get(0);
    }

}
