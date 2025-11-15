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

    @Override
    public List<Producto> findByFiltros(String nombre, Integer tipoProductoId) {
        StringBuilder jpql = new StringBuilder("SELECT p FROM Producto p WHERE 1=1");
        
        if (nombre != null && !nombre.trim().isEmpty()) {
            jpql.append(" AND LOWER(p.nombre) LIKE LOWER(:nombre)");
        }
        if (tipoProductoId != null) {
            jpql.append(" AND p.tipoProducto.id = :tipoProductoId");
        }
        
        TypedQuery<Producto> query = entityManager.createQuery(jpql.toString(), Producto.class);
        
        if (nombre != null && !nombre.trim().isEmpty()) {
            query.setParameter("nombre", "%" + nombre + "%");
        }
        if (tipoProductoId != null) {
            query.setParameter("tipoProductoId", tipoProductoId);
        }
        
        return query.getResultList();
    }

    @Transactional
    @Override
    public void delete(Producto producto) {
        if (entityManager.contains(producto)) {
            entityManager.remove(producto);
        } else {
            entityManager.remove(entityManager.merge(producto));
        }
    }

    @Override
    public Long count() {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(p) FROM Producto p", Long.class);
        return query.getSingleResult();
    }

    @Override
    public boolean existsById(Integer id) {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(p) FROM Producto p WHERE p.id = :id", Long.class);
        query.setParameter("id", id);
        return query.getSingleResult() > 0;
    }


}
