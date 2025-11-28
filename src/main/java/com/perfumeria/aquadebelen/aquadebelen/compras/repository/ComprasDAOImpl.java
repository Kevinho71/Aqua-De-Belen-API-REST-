package com.perfumeria.aquadebelen.aquadebelen.compras.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.perfumeria.aquadebelen.aquadebelen.compras.model.Compra;
import com.perfumeria.aquadebelen.aquadebelen.inventario.model.Producto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Repository
public class ComprasDAOImpl implements ComprasDAO{

    private EntityManager entityManager;

    public ComprasDAOImpl(EntityManager entityManager){
        this.entityManager= entityManager;
    }

    @Transactional
    @Override
    public void store(Compra compra) {
        if (compra.getId() == null || entityManager.find(Compra.class, compra.getId()) == null) {
            entityManager.persist(compra);
        } else {
            entityManager.merge(compra);
        }
        entityManager.flush();
    }

    @Transactional
    @Override
    public void deleteById(Integer id) {
        entityManager.createQuery("DELETE FROM Compra c WHERE c.id = :data").setParameter("data", id).executeUpdate();
    }

    @Override
    public Compra findById(Integer id) {
        TypedQuery<Compra> query = entityManager.createQuery("SELECT c FROM Compra c WHERE c.id= :data", Compra.class);
        query.setParameter("data", id);
        return query.getSingleResult();
    }

    @Override
    public List<Compra> findAll() {
        TypedQuery<Compra> query = entityManager.createQuery("SELECT c FROM Compra c ORDER BY c.fecha DESC", Compra.class);
        return query.getResultList();
    }

    @Override
    public List<Compra> findAll(int page, int size) {
        TypedQuery<Compra> query = entityManager.createQuery("SELECT c FROM Compra c ORDER BY c.fecha DESC", Compra.class);
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        return query.getResultList();
    }

    @Override
    public Integer nextId() {
       TypedQuery<Integer> query = entityManager.createQuery("SELECT COALESCE(MAX(c.id), 0) FROM Compra c ",
                Integer.class);
        return query.getSingleResult() + 1;
    }

    @Override
    public List<Compra> findByFilters(Integer proveedorId, java.time.LocalDateTime fechaInicio, java.time.LocalDateTime fechaFin) {
        StringBuilder jpql = new StringBuilder("SELECT c FROM Compra c WHERE 1=1");
        
        if (proveedorId != null) {
            jpql.append(" AND c.proveedor.id = :proveedorId");
        }
        if (fechaInicio != null) {
            jpql.append(" AND c.fecha >= :fechaInicio");
        }
        if (fechaFin != null) {
            jpql.append(" AND c.fecha <= :fechaFin");
        }
        
        jpql.append(" ORDER BY c.fecha DESC");
        
        TypedQuery<Compra> query = entityManager.createQuery(jpql.toString(), Compra.class);
        
        if (proveedorId != null) {
            query.setParameter("proveedorId", proveedorId);
        }
        if (fechaInicio != null) {
            query.setParameter("fechaInicio", fechaInicio);
        }
        if (fechaFin != null) {
            query.setParameter("fechaFin", fechaFin);
        }
        
        return query.getResultList();
    }

    @Override
    public List<Object[]> findUltimaCompraPorProducto(Integer productoId) {
        String jpql = "SELECT c.proveedor.id, c.proveedor.nombre " +
                     "FROM Compra c " +
                     "JOIN c.lote l " +
                     "JOIN l.sublotes s " +
                     "WHERE s.producto.id = :productoId " +
                     "ORDER BY c.fecha DESC";
        
        TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
        query.setParameter("productoId", productoId);
        query.setMaxResults(1);
        
        return query.getResultList();
    }

}
