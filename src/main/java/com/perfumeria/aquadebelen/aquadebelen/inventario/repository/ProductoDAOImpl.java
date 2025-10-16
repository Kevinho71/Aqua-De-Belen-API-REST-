package com.perfumeria.aquadebelen.aquadebelen.inventario.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.perfumeria.aquadebelen.aquadebelen.inventario.model.Producto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Repository
public class ProductoDAOImpl implements ProductoDAO{
    private EntityManager entityManager;

    public ProductoDAOImpl(EntityManager entityManager){
        this.entityManager=entityManager;
    }

    @Override
    public Producto findById(Integer id) {
        TypedQuery<Producto> query = entityManager.createQuery(("SELECT p FROM Producto p WHERE p.id = :data"), Producto.class);
        query.setParameter("data", id);
        return query.getSingleResult();
    }

    @Transactional
    @Override
    public void store(Producto producto) {
        entityManager.merge(producto);
        entityManager.flush();
    }

    @Transactional
    @Override
    public void deleteById(Integer id) {
        entityManager.createQuery("DELETE FROM Producto p WHERE p.id =:data").setParameter("data", id).executeUpdate();
    }

    @Override
    public List<Producto> list() {
       TypedQuery<Producto> query = entityManager.createQuery("SELECT p FROM Producto p", Producto.class);
       List<Producto> lista = query.getResultList();
       return lista;
    }

    @Override
    public Integer nextId() {
        TypedQuery<Integer> query = entityManager.createQuery("SELECT COALESCE(MAX(p.id), 0) FROM Producto p ",
                Integer.class);
        return query.getSingleResult() + 1;
    }
}
