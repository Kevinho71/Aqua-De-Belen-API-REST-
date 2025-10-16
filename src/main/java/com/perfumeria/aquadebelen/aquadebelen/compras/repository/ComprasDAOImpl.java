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
        entityManager.merge(compra);
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
        TypedQuery<Compra> query = entityManager.createQuery("SELECT c FROM Compra c", Compra.class);
        return query.getResultList();
    }

    @Override
    public Integer nextId() {
       TypedQuery<Integer> query = entityManager.createQuery("SELECT COALESCE(MAX(p.id), 0) FROM Compra c ",
                Integer.class);
        return query.getSingleResult() + 1;
    }

}
