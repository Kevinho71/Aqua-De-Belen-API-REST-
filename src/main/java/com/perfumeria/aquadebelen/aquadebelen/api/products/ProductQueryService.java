package com.perfumeria.aquadebelen.aquadebelen.api.products;

import java.util.List;

import org.springframework.stereotype.Service;

import com.perfumeria.aquadebelen.aquadebelen.api.products.dto.ProductView;
import com.perfumeria.aquadebelen.aquadebelen.core.inventory.InventoryQueryService;
import com.perfumeria.aquadebelen.aquadebelen.model.productos.domain.Producto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class ProductQueryService {

    @PersistenceContext
    private EntityManager em;

    private final InventoryQueryService inventory;

    public ProductQueryService(InventoryQueryService inventory){
        this.inventory = inventory;
    }

    public List<ProductView> listAll(){
        List<Producto> productos = em.createQuery("""
            select p from Producto p
            left join fetch p.tipoProducto
        """, Producto.class).getResultList();

        var stockMap = inventory.stockFor(productos.stream().map(Producto::getId).toList());

        return productos.stream().map(p ->
            new ProductView(
                p.getId(),
                p.getNombre(),
                p.getDescripcion(),
                p.getPrecio() == null ? 0.0 : p.getPrecio(),
                stockMap.getOrDefault(p.getId(), 0),
                p.getTipoProducto() != null ? p.getTipoProducto().getNombre() : null
            )
        ).toList();
    }
}
