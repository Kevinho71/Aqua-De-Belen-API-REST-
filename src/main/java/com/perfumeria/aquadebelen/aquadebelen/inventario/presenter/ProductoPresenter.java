package com.perfumeria.aquadebelen.aquadebelen.inventario.presenter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.perfumeria.aquadebelen.aquadebelen.inventario.DTO.ProductoDTOResponse;
import com.perfumeria.aquadebelen.aquadebelen.inventario.viewmodel.ListProductoViewModel;
import com.perfumeria.aquadebelen.aquadebelen.inventario.viewmodel.ProductoViewModel;

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
            lista.add(pvm);
        }
        return lista;
    }

}
