package com.perfumeria.aquadebelen.aquadebelen.inventario.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.perfumeria.aquadebelen.aquadebelen.compras.model.PrecioHistorico;
import com.perfumeria.aquadebelen.aquadebelen.compras.repository.PrecioHistoricoDAO;
import com.perfumeria.aquadebelen.aquadebelen.inventario.DTO.ProductoDTORequest;
import com.perfumeria.aquadebelen.aquadebelen.inventario.DTO.ProductoDTOResponse;
import com.perfumeria.aquadebelen.aquadebelen.inventario.model.Producto;
import com.perfumeria.aquadebelen.aquadebelen.inventario.repository.ProductoDAO;
import com.perfumeria.aquadebelen.aquadebelen.inventario.repository.TipoProductoDAO;


@Service
public class ProductoService {

    private final ProductoDAO pDAO;
    private final TipoProductoDAO tpDAO;
    private final PrecioHistoricoDAO phDAO;

    public ProductoService(ProductoDAO pDAO, TipoProductoDAO tpDAO, PrecioHistoricoDAO phDAO) {
        this.pDAO = pDAO;
        this.tpDAO = tpDAO;
        this.phDAO = phDAO;
    }


    
    public ProductoDTOResponse store(Integer id, ProductoDTORequest req) {
        Producto producto = new Producto();
        if (id == null) {
            producto.setId(pDAO.nextId());
         //   producto.setPrecio(req.precio()); //IR QUITANDO ESTE CAMPO PROGRESIVAMENTE
            producto.setDescripcion(req.descripcion());
            producto.setNombre(req.nombre());
            producto.setTipoProducto(tpDAO.findById(req.tipoProductoId()));
            pDAO.store(producto);
            agregarPrecioHistorico(producto, req.precio()); 
        } else {
            producto = pDAO.findById(id);
         //   producto.setPrecio(req.precio());
            producto.setDescripcion(req.descripcion());
            producto.setNombre(req.nombre());
            producto.setTipoProducto(tpDAO.findById(req.tipoProductoId()));
            pDAO.store(producto);
            agregarPrecioHistorico(producto, req.precio());
        }
        Producto producto2 = pDAO.findById(producto.getId());
        return mapToDtoResponse(producto2);
    }


    public void agregarPrecioHistorico(Producto producto, double precio){
        PrecioHistorico ph = new PrecioHistorico();
        ph.setProducto(producto);
        ph.setPrecioVenta(precio);
        ph.setFecha(LocalDateTime.now());
        producto.addPrecioHistorico(ph);
        phDAO.save(ph);
    }

    // BUSCAR UN PRODUCTO
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

    public List<ProductoDTOResponse> buscarPorFiltros(String nombre, Integer tipoProductoId) {
        List<Producto> lista = pDAO.findByFiltros(nombre, tipoProductoId);
        List<ProductoDTOResponse> listaResp = new ArrayList<>();
        for (Producto p : lista) {
            ProductoDTOResponse e = mapToDtoResponse(p);
            listaResp.add(e);
        }
        return listaResp;
    }

    public ProductoDTOResponse mapToDtoResponse(Producto producto) {
        return new ProductoDTOResponse(producto.getId(),
                phDAO.findUltimoPrecioByProductoId(producto.getId()).getPrecioVenta(), producto.getDescripcion(),
                producto.getNombre(), producto.getTipoProducto().getNombre());
    }
}
