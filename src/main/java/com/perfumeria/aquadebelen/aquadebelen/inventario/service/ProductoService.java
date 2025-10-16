package com.perfumeria.aquadebelen.aquadebelen.inventario.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.perfumeria.aquadebelen.aquadebelen.inventario.DTO.ProductoDTORequest;
import com.perfumeria.aquadebelen.aquadebelen.inventario.DTO.ProductoDTOResponse;
import com.perfumeria.aquadebelen.aquadebelen.inventario.model.Producto;
import com.perfumeria.aquadebelen.aquadebelen.inventario.repository.ProductoDAO;
import com.perfumeria.aquadebelen.aquadebelen.inventario.repository.TipoProductoDAO;


@Service
public class ProductoService {

    private final ProductoDAO pDAO;
    private final TipoProductoDAO tpDAO;

    public ProductoService(ProductoDAO pDAO, TipoProductoDAO tpDAO) {
        this.pDAO = pDAO;
        this.tpDAO = tpDAO;
    }


    
    public ProductoDTOResponse store(Integer id, ProductoDTORequest req) {
        Producto producto = new Producto();
        if (id == null) {
            producto.setId(pDAO.nextId());
            producto.setPrecio(req.precio());
            producto.setDescripcion(req.descripcion());
            producto.setNombre(req.nombre());
            producto.setTipoProducto(tpDAO.findById(req.tipoProductoId()));
            pDAO.store(producto);
        } else {
            producto = pDAO.findById(id);
            producto.setPrecio(req.precio());
            producto.setDescripcion(req.descripcion());
            producto.setNombre(req.nombre());
            producto.setTipoProducto(tpDAO.findById(req.tipoProductoId()));
            pDAO.store(producto);
        }
        Producto producto2 = pDAO.findById(producto.getId());
        return mapToDtoResponse(producto2);
    }


    // BORRAR UNA Ventas
    public void borrar(Integer id) {
        pDAO.deleteById(id);
    }

    // BUSCAR UNA Ventas
    public ProductoDTOResponse buscar(Integer id) {
        Producto producto = pDAO.findById(id);
        return mapToDtoResponse(producto);
    }

    public List<ProductoDTOResponse> listar() {
        List<Producto> lista = pDAO.list();
        List<ProductoDTOResponse> listaResp = new ArrayList<>();
        for (Producto p : lista) {
            ProductoDTOResponse e = mapToDtoResponse(p);
            listaResp.add(e);
        }
        return listaResp;
    }

    public ProductoDTOResponse mapToDtoResponse(Producto producto) {

        return new ProductoDTOResponse(producto.getId(),
                producto.getPrecio(), producto.getDescripcion(),
                producto.getNombre(), producto.getTipoProducto().getNombre());
    }
}
