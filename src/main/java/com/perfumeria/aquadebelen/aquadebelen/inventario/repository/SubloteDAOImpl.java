package com.perfumeria.aquadebelen.aquadebelen.inventario.repository;

import java.time.LocalDate;
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
        TypedQuery<Sublote> query = entityManager.createQuery("SELECT s FROM Sublote s ORDER BY s.fechaVencimiento DESC", Sublote.class);
        return query.getResultList();
    }

    @Override
    public List<Sublote> list(int page, int size) {
        TypedQuery<Sublote> query = entityManager.createQuery("SELECT s FROM Sublote s ORDER BY s.fechaVencimiento DESC", Sublote.class);
        query.setFirstResult(page * size);
        query.setMaxResults(size);
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

    @Override
    public List<Sublote> findDisponibles() {
        TypedQuery<Sublote> query = entityManager.createQuery(
                "SELECT s FROM Sublote s WHERE s.cantidadActual > 0 " +
                "AND s.estado != 'AGOTADO' ORDER BY s.fechaVencimiento ASC", 
                Sublote.class);
        return query.getResultList();
    }

    @Override
    public List<Sublote> findProximosAVencer(Integer dias) {
        LocalDate fechaLimite = LocalDate.now().plusDays(dias);
        TypedQuery<Sublote> query = entityManager.createQuery(
                "SELECT s FROM Sublote s WHERE s.cantidadActual > 0 " +
                "AND s.estado != 'AGOTADO' " +
                "AND s.fechaVencimiento <= :fechaLimite " +
                "AND s.fechaVencimiento >= CURRENT_DATE " +
                "ORDER BY s.fechaVencimiento ASC", 
                Sublote.class);
        query.setParameter("fechaLimite", fechaLimite);
        return query.getResultList();
    }

    @Override
    public Double sumCantidadActualByProductoId(Integer productoId) {
        TypedQuery<Double> query = entityManager.createQuery(
                "SELECT COALESCE(SUM(s.cantidadActual), 0) FROM Sublote s WHERE s.producto.id = :productoId",
                Double.class);
        query.setParameter("productoId", productoId);
        return query.getSingleResult();
    }

    @Override
    public List<Object[]> sumCantidadActualGroupByProducto() {
        TypedQuery<Object[]> query = entityManager.createQuery(
            "SELECT s.producto.id, COALESCE(SUM(s.cantidadActual), 0) "
                + "FROM Sublote s WHERE s.producto IS NOT NULL GROUP BY s.producto.id",
                Object[].class);
        return query.getResultList();
    }

    @Override
    public List<Sublote> findByProductoId(Integer productoId) {
        TypedQuery<Sublote> query = entityManager.createQuery(
                "SELECT s FROM Sublote s WHERE s.producto.id = :productoId " +
                "ORDER BY s.fechaVencimiento ASC", 
                Sublote.class);
        query.setParameter("productoId", productoId);
        return query.getResultList();
    }

    @Override
    public List<Sublote> findByFilters(Integer productoId, String estado) {
        StringBuilder jpql = new StringBuilder("SELECT s FROM Sublote s WHERE 1=1");
        
        if (productoId != null) {
            jpql.append(" AND s.producto.id = :productoId");
        }
        if (estado != null && !estado.isEmpty()) {
            jpql.append(" AND s.estado = :estado");
        }
        
        jpql.append(" ORDER BY s.fechaVencimiento ASC");
        
        TypedQuery<Sublote> query = entityManager.createQuery(jpql.toString(), Sublote.class);
        
        if (productoId != null) {
            query.setParameter("productoId", productoId);
        }
        if (estado != null && !estado.isEmpty()) {
            query.setParameter("estado", com.perfumeria.aquadebelen.aquadebelen.inventario.model.EstadoSublote.valueOf(estado));
        }
        
        return query.getResultList();
    }

}
