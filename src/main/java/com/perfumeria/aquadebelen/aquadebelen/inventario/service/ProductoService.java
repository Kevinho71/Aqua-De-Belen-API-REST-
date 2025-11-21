package com.perfumeria.aquadebelen.aquadebelen.inventario.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.perfumeria.aquadebelen.aquadebelen.compras.model.PrecioHistorico;
import com.perfumeria.aquadebelen.aquadebelen.compras.repository.PrecioHistoricoDAO;
import com.perfumeria.aquadebelen.aquadebelen.inventario.DTO.ProductoDTORequest;
import com.perfumeria.aquadebelen.aquadebelen.inventario.DTO.ProductoDTOResponse;
import com.perfumeria.aquadebelen.aquadebelen.inventario.model.Producto;
import com.perfumeria.aquadebelen.aquadebelen.inventario.repository.ProductoDAO;
import com.perfumeria.aquadebelen.aquadebelen.inventario.repository.TipoProductoDAO;
import com.perfumeria.aquadebelen.aquadebelen.inventario.repository.SubloteDAO;
import com.perfumeria.aquadebelen.aquadebelen.inventario.viewmodel.ProductoStockTotalViewModel;


@Service
public class ProductoService {

    private final ProductoDAO pDAO;
    private final TipoProductoDAO tpDAO;
    private final PrecioHistoricoDAO phDAO;
    private final SubloteDAO subloteDAO;

    public ProductoService(ProductoDAO pDAO, TipoProductoDAO tpDAO, PrecioHistoricoDAO phDAO, SubloteDAO subloteDAO) {
        this.pDAO = pDAO;
        this.tpDAO = tpDAO;
        this.phDAO = phDAO;
        this.subloteDAO = subloteDAO;
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
        ph.setId(phDAO.nextId());
        ph.setProducto(producto);
        ph.setPrecioVenta(precio);
        ph.setFecha(LocalDateTime.now());
        producto.addPrecioHistorico(ph);
        phDAO.save(ph);
    }

    public ProductoDTOResponse actualizarPrecio(Integer productoId, double nuevoPrecio) {
        Producto producto = pDAO.findById(productoId);
        agregarPrecioHistorico(producto, nuevoPrecio);
        return mapToDtoResponse(producto);
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
            producto.getNombre(), producto.getTipoProducto().getNombre(), producto.getTipoProducto().getId());
    }

    public void eliminar(Integer id) {
        Producto producto = pDAO.findById(id);
        pDAO.delete(producto);
    }

    public Long contarProductos() {
        return pDAO.count();
    }

    public boolean existeProducto(Integer id) {
        return pDAO.existsById(id);
    }

    public List<ProductoStockTotalViewModel> obtenerStockTotalProductos() {
        Map<Integer, Double> stockPorProducto = new HashMap<>();
        List<Object[]> resultados = subloteDAO.sumCantidadActualGroupByProducto();
        for (Object[] fila : resultados) {
            if (fila == null || fila.length < 2 || fila[0] == null) {
                continue;
            }
            Integer productoId = ((Number) fila[0]).intValue();
            Double cantidad = fila[1] != null ? ((Number) fila[1]).doubleValue() : 0d;
            stockPorProducto.put(productoId, cantidad);
        }

        List<Producto> productos = pDAO.list();
        List<ProductoStockTotalViewModel> respuesta = new ArrayList<>();
        for (Producto producto : productos) {
            double total = stockPorProducto.getOrDefault(producto.getId(), 0d);
            respuesta.add(new ProductoStockTotalViewModel(producto.getId(), producto.getNombre(), total));
        }
        return respuesta;
    }

    public ProductoStockTotalViewModel obtenerStockTotalProducto(Integer id) {
        Producto producto = pDAO.findById(id);
        Double cantidad = subloteDAO.sumCantidadActualByProductoId(id);
        double total = cantidad != null ? cantidad : 0d;
        return new ProductoStockTotalViewModel(producto.getId(), producto.getNombre(), total);
    }
}
