package com.perfumeria.aquadebelen.aquadebelen.inventario.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.perfumeria.aquadebelen.aquadebelen.inventario.model.Lote;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Repository
public class LoteDAOImpl implements LoteDAO {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Lote findById(Integer id) {
        TypedQuery<Lote> query = entityManager.createQuery("SELECT l FROM Lote l WHERE l.id = :id", Lote.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Transactional
    @Override
    public void store(Lote lote) {
        entityManager.merge(lote);
        entityManager.flush();
    }

    @Override
    public List<Lote> list() {
        TypedQuery<Lote> query = entityManager.createQuery("SELECT l FROM Lote l", Lote.class);
        return query.getResultList();
    }

    @Override
    public Integer nextId() {
        TypedQuery<Integer> query = entityManager.createQuery("SELECT COALESCE(MAX(l.id), 0) FROM Lote l ",
                Integer.class);
        return query.getSingleResult() + 1;
    }

}
