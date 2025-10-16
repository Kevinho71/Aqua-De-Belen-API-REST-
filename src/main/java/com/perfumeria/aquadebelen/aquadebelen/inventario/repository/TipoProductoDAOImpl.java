package com.perfumeria.aquadebelen.aquadebelen.inventario.repository;

import org.springframework.stereotype.Repository;

import com.perfumeria.aquadebelen.aquadebelen.inventario.model.TipoProducto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Repository
public class TipoProductoDAOImpl implements TipoProductoDAO{
    private EntityManager entityManager;

    public TipoProductoDAOImpl(EntityManager entityManager){
        this.entityManager=entityManager;
    }

    @Override
    public TipoProducto findById(Integer id) {
        TypedQuery<TipoProducto> query = entityManager.createQuery("SELECT t FROM TipoProducto t WHERE t.id = :data", TipoProducto.class);
        query.setParameter("data", id);
        return query.getSingleResult();

    }
}
