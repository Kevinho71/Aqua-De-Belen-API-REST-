package com.perfumeria.aquadebelen.aquadebelen.ventas.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.perfumeria.aquadebelen.aquadebelen.ventas.model.DetalleVenta;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Repository
public class DetalleVentaDAOImpl implements DetalleVentaDAO {

    private EntityManager entityManager;

    public DetalleVentaDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Integer nextId() {
        Integer maxId = entityManager.createQuery("SELECT COALESCE(MAX(dv.id), 0) FROM DetalleVenta dv", Integer.class)
                .getSingleResult();
        return maxId + 1;
    }

    @Override
    public List<DetalleVenta> buscarTransaccionesPorIdTransaccion(Integer id) {
        TypedQuery<DetalleVenta> query = entityManager
                .createQuery("SELECT d FROM DetalleTransaccion d WHERE d.transaccion.id = :data ", DetalleVenta.class);
        query.setParameter("data", id);
        List<DetalleVenta> lista = query.getResultList();
        return lista;
    }

}
