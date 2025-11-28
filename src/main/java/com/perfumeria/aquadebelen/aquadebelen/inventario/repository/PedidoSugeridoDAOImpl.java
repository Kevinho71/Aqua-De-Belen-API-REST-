package com.perfumeria.aquadebelen.aquadebelen.inventario.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.perfumeria.aquadebelen.aquadebelen.inventario.model.PedidoSugerido;
import com.perfumeria.aquadebelen.aquadebelen.inventario.model.PedidoSugerido.EstadoPedidoSugerido;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Repository
public class PedidoSugeridoDAOImpl implements PedidoSugeridoDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public PedidoSugerido store(PedidoSugerido pedidoSugerido) {
        if (pedidoSugerido.getId() == null) {
            entityManager.persist(pedidoSugerido);
            return pedidoSugerido;
        } else {
            return entityManager.merge(pedidoSugerido);
        }
    }

    @Override
    public PedidoSugerido findById(Integer id) {
        return entityManager.find(PedidoSugerido.class, id);
    }

    @Override
    public List<PedidoSugerido> findAll() {
        TypedQuery<PedidoSugerido> query = entityManager.createQuery(
            "SELECT p FROM PedidoSugerido p ORDER BY p.fechaSugerida DESC", 
            PedidoSugerido.class
        );
        return query.getResultList();
    }

    @Override
    public List<PedidoSugerido> findByEstado(EstadoPedidoSugerido estado) {
        TypedQuery<PedidoSugerido> query = entityManager.createQuery(
            "SELECT p FROM PedidoSugerido p WHERE p.estado = :estado ORDER BY p.fechaSugerida DESC", 
            PedidoSugerido.class
        );
        query.setParameter("estado", estado);
        return query.getResultList();
    }

    @Override
    public List<PedidoSugerido> findByProductoId(Integer productoId) {
        TypedQuery<PedidoSugerido> query = entityManager.createQuery(
            "SELECT p FROM PedidoSugerido p WHERE p.producto.id = :productoId ORDER BY p.fechaSugerida DESC", 
            PedidoSugerido.class
        );
        query.setParameter("productoId", productoId);
        return query.getResultList();
    }

    @Override
    public void delete(Integer id) {
        PedidoSugerido pedidoSugerido = findById(id);
        if (pedidoSugerido != null) {
            entityManager.remove(pedidoSugerido);
        }
    }
}
