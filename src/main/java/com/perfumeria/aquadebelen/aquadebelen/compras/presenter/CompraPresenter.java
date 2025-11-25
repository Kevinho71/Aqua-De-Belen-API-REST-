package com.perfumeria.aquadebelen.aquadebelen.compras.presenter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.perfumeria.aquadebelen.aquadebelen.compras.DTO.CompraDTOResponse;
import com.perfumeria.aquadebelen.aquadebelen.compras.DTO.DetalleCompraDTOResponse;
import com.perfumeria.aquadebelen.aquadebelen.compras.viewmodel.CompraViewModel;
import com.perfumeria.aquadebelen.aquadebelen.compras.viewmodel.DetalleCompraViewModel;
import com.perfumeria.aquadebelen.aquadebelen.compras.viewmodel.ListaCompraViewModel;

@Component
public class CompraPresenter {

    public CompraViewModel present(CompraDTOResponse compraDTOResponse){
        CompraViewModel compraViewModel = new CompraViewModel();
        compraViewModel.setId(String.valueOf(compraDTOResponse.id()));
        compraViewModel.setCostoBruto(String.valueOf(compraDTOResponse.costoBruto())+" Bs");
        compraViewModel.setCostoNeto(String.valueOf(compraDTOResponse.costoNeto())+" Bs");
        compraViewModel.setDescuentoTotal(String.valueOf(compraDTOResponse.descuentoTotal())+ " Bs");

        compraViewModel.setProveedor(compraDTOResponse.proveedor());
        compraViewModel.setFecha(compraDTOResponse.fecha());
        compraViewModel.setLoteId(compraDTOResponse.loteId());
        compraViewModel.setDetalles(presentDetalles(compraDTOResponse.detalles()));

        return compraViewModel;
    }

    public List<DetalleCompraViewModel> presentDetalles(List<DetalleCompraDTOResponse> listResp){

        List<DetalleCompraViewModel> lista = new ArrayList<>();
        for(DetalleCompraDTOResponse dcd : listResp){
            DetalleCompraViewModel dcv = new DetalleCompraViewModel();
            dcv.setCompraId(String.valueOf(dcd.compraId()));
            dcv.setCostoUnitario(String.valueOf(dcd.costoUnitario())+ " Bs");
            dcv.setDescuento(String.valueOf(dcd.descuento())+" Bs");
            dcv.setProducto(dcd.producto());
            dcv.setTipoProducto(dcd.tipoProducto());
            dcv.setSubtotal(String.valueOf(dcd.subtotal())+" Bs");
            dcv.setCantidad(String.valueOf(dcd.cantidad()));

            lista.add(dcv);
        }

        return lista;
        
    }


    public List<ListaCompraViewModel> presentList(List<CompraDTOResponse> listResp){
        List<ListaCompraViewModel> lista = new ArrayList<>();
        for(CompraDTOResponse cdr : listResp){
            ListaCompraViewModel lcv = new ListaCompraViewModel();
            lcv.setId(cdr.id());
            lcv.setProveedor(cdr.proveedor());
            lcv.setCostoBruto(String.valueOf(cdr.costoBruto())+" Bs");
            lcv.setCostoNeto(String.valueOf(cdr.costoNeto())+" Bs");
            lcv.setDescuentoTotal(String.valueOf(cdr.descuentoTotal())+" Bs");
            lcv.setFecha(cdr.fecha());
            lcv.setLoteId(cdr.loteId());
            lista.add(lcv);
        }
        return lista;
    }

}
