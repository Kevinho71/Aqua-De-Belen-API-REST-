package com.perfumeria.aquadebelen.aquadebelen.inventario.presenter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.perfumeria.aquadebelen.aquadebelen.inventario.DTO.ProductoDTOResponse;
import com.perfumeria.aquadebelen.aquadebelen.inventario.model.TipoProducto;
import com.perfumeria.aquadebelen.aquadebelen.inventario.viewmodel.ListProductoViewModel;
import com.perfumeria.aquadebelen.aquadebelen.inventario.viewmodel.ProductoViewModel;
import com.perfumeria.aquadebelen.aquadebelen.inventario.viewmodel.TipoProductoViewModel;

@Component
public class ProductoPresenter {


    public ProductoViewModel present(ProductoDTOResponse res) {
        ProductoViewModel pvm = new ProductoViewModel();
        pvm.setProductoId(String.valueOf(res.productoId()));
        pvm.setNombre(res.nombre());
        pvm.setDescripcion(res.descripcion());
        pvm.setPrecio(String.valueOf(res.precio()) + " Bs");
        pvm.setTipoProducto(res.tipoProducto());
        pvm.setTipoProductoId(res.tipoProductoId());
        pvm.setDescontinuado(res.descontinuado());

        return pvm;
    }

    public List<ListProductoViewModel> presentList(List<ProductoDTOResponse> listResp) {
        List<ListProductoViewModel> lista = new ArrayList<>();
        for (ProductoDTOResponse res : listResp) {
            ListProductoViewModel pvm = new ListProductoViewModel();
            pvm.setId(res.productoId());
            pvm.setNombre(res.nombre());
            pvm.setDescripcion(res.descripcion());
            pvm.setPrecio(String.valueOf(res.precio()) + " Bs");
            pvm.setTipoProducto(res.tipoProducto());
            pvm.setDescontinuado(res.descontinuado());
            lista.add(pvm);
        }
        return lista;
    }

    public List<TipoProductoViewModel> presentTipos(List<TipoProducto> tipos) {
        List<TipoProductoViewModel> lista = new ArrayList<>();
        for (TipoProducto t : tipos) {
            lista.add(new TipoProductoViewModel(t.getId(), t.getNombre()));
        }
        return lista;
    }

}
