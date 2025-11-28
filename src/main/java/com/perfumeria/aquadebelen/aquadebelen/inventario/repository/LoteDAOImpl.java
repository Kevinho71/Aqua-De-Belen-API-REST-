package com.perfumeria.aquadebelen.aquadebelen.inventario.repository;

import java.time.LocalDateTime;
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
        TypedQuery<Lote> query = entityManager.createQuery("SELECT l FROM Lote l ORDER BY l.fechaIngreso DESC", Lote.class);
        return query.getResultList();
    }

    @Override
    public List<Lote> list(int page, int size) {
        TypedQuery<Lote> query = entityManager.createQuery("SELECT l FROM Lote l ORDER BY l.fechaIngreso DESC", Lote.class);
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        return query.getResultList();
    }

    @Override
    public Integer nextId() {
        TypedQuery<Integer> query = entityManager.createQuery("SELECT COALESCE(MAX(l.id), 0) FROM Lote l ",
                Integer.class);
        return query.getSingleResult() + 1;
    }

    @Override
    public List<Lote> findByFilters(Integer compraId, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        StringBuilder jpql = new StringBuilder("SELECT l FROM Lote l WHERE 1=1");
        
        if (compraId != null) {
            jpql.append(" AND l.compra.id = :compraId");
        }
        if (fechaInicio != null) {
            jpql.append(" AND l.fechaIngreso >= :fechaInicio");
        }
        if (fechaFin != null) {
            jpql.append(" AND l.fechaIngreso <= :fechaFin");
        }
        
        jpql.append(" ORDER BY l.fechaIngreso DESC");
        
        TypedQuery<Lote> query = entityManager.createQuery(jpql.toString(), Lote.class);
        
        if (compraId != null) {
            query.setParameter("compraId", compraId);
        }
        if (fechaInicio != null) {
            query.setParameter("fechaInicio", fechaInicio);
        }
        if (fechaFin != null) {
            query.setParameter("fechaFin", fechaFin);
        }
        
        return query.getResultList();
    }

}
