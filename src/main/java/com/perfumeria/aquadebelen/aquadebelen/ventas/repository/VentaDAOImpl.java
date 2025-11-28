package com.perfumeria.aquadebelen.aquadebelen.ventas.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.perfumeria.aquadebelen.aquadebelen.ventas.model.Venta;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Repository
public class VentaDAOImpl implements VentaDAO {

    private EntityManager entityManager;

    public VentaDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    @Override
    public void store(Venta Venta) {
        entityManager.merge(Venta);
        entityManager.flush();
    }

    @Override
    public Venta findById(Integer id) {
        TypedQuery<Venta> query = entityManager.createQuery(
                "SELECT t FROM Venta t" + " JOIN FETCH t.detallesVentas" + " JOIN FETCH t.metodoDePago"
                        + " WHERE t.id =:data",
                Venta.class);
        query.setParameter("data", id);
        return query.getSingleResult();
    }

    @Transactional
    @Override
    public void deleteById(Integer id) {

        entityManager.createQuery("DELETE FROM DetalleVenta d WHERE d.venta.id =: data")
                .setParameter("data", id).executeUpdate();

        entityManager.createQuery("DELETE FROM Venta t WHERE t.id = :data").setParameter("data", id)
                .executeUpdate();
    }

    @Override
    public List<Venta> findALL() {
        TypedQuery<Venta> query = entityManager.createQuery("SELECT t FROM Venta t ORDER BY t.fecha DESC", Venta.class);
        List<Venta> lista = query.getResultList();
        return lista;
    }

    @Override
    public List<Venta> findALL(int page, int size) {
        TypedQuery<Venta> query = entityManager.createQuery("SELECT t FROM Venta t ORDER BY t.fecha DESC", Venta.class);
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        List<Venta> lista = query.getResultList();
        return lista;
    }

    @Override
    public Integer nextId() {
        TypedQuery<Integer> query = entityManager.createQuery("SELECT COALESCE(MAX(t.id), 0) FROM Venta t ",
                Integer.class);
        return query.getSingleResult() + 1;

    }

    @Override
    public List<Venta> findByFilters(Integer clienteId, java.time.LocalDateTime fechaInicio, java.time.LocalDateTime fechaFin) {
        StringBuilder jpql = new StringBuilder("SELECT v FROM Venta v WHERE 1=1");
        
        if (clienteId != null) {
            jpql.append(" AND v.cliente.id = :clienteId");
        }
        if (fechaInicio != null) {
            jpql.append(" AND v.fecha >= :fechaInicio");
        }
        if (fechaFin != null) {
            jpql.append(" AND v.fecha <= :fechaFin");
        }
        
        jpql.append(" ORDER BY v.fecha DESC");
        
        TypedQuery<Venta> query = entityManager.createQuery(jpql.toString(), Venta.class);
        
        if (clienteId != null) {
            query.setParameter("clienteId", clienteId);
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
    public List<Object[]> sumVentasPorProductoUltimoAnio() {
        java.time.LocalDateTime haceUnAnio = java.time.LocalDateTime.now().minusYears(1);
        TypedQuery<Object[]> query = entityManager.createQuery(
            "SELECT d.producto.id, SUM(d.cantidad) " +
            "FROM DetalleVenta d " +
            "JOIN d.venta v " +
            "WHERE v.fecha >= :fechaLimite " +
            "GROUP BY d.producto.id", Object[].class);
        query.setParameter("fechaLimite", haceUnAnio);
        return query.getResultList();
    }

}
