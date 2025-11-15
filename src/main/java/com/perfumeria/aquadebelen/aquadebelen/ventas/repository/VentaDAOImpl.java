package com.perfumeria.aquadebelen.aquadebelen.ventas.repository;

import java.util.List;

import javax.management.Query;

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
        TypedQuery<Venta> query = entityManager.createQuery("SELECT t FROM Venta t ORDER BY t.id ASC", Venta.class);
        List<Venta> lista = query.getResultList();
        return lista;
    }

    @Override
    public Integer nextId() {
        TypedQuery<Integer> query = entityManager.createQuery("SELECT COALESCE(MAX(t.id), 0) FROM Venta t ",
                Integer.class);
        return query.getSingleResult() + 1;

    }

}
