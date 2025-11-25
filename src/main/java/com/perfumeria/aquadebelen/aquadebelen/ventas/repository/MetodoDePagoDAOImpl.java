package com.perfumeria.aquadebelen.aquadebelen.ventas.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.perfumeria.aquadebelen.aquadebelen.ventas.model.MetodoDePago;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Repository
public class MetodoDePagoDAOImpl implements MetodoDePagoDAO{

    private EntityManager entityManager;

    public MetodoDePagoDAOImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public MetodoDePago findById(Integer id) {
      return  entityManager.find(MetodoDePago.class, id);
    }

    @Override
    public List<MetodoDePago> list() {
        TypedQuery<MetodoDePago> query = entityManager.createQuery("SELECT m FROM MetodoDePago m ORDER BY m.metodo",
                MetodoDePago.class);
        return query.getResultList();
    }


}
