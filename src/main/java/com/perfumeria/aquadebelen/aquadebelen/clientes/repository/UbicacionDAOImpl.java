package com.perfumeria.aquadebelen.aquadebelen.clientes.repository;

import org.springframework.stereotype.Repository;

import com.perfumeria.aquadebelen.aquadebelen.clientes.model.Ubicacion;

import jakarta.persistence.EntityManager;

@Repository
public class UbicacionDAOImpl implements UbicacionDAO{

    private EntityManager entityManager;

    public UbicacionDAOImpl(EntityManager entityManager){
        this.entityManager=entityManager;
    }

    @Override
    public Ubicacion findById(Integer id) {
        return entityManager.find(Ubicacion.class, id);
    }

    @Override
    public java.util.List<Ubicacion> list() {
        return entityManager.createQuery("FROM Ubicacion", Ubicacion.class).getResultList();
    }
}
