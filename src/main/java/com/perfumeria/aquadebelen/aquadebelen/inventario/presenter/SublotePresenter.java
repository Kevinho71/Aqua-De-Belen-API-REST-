package com.perfumeria.aquadebelen.aquadebelen.inventario.presenter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.perfumeria.aquadebelen.aquadebelen.inventario.DTO.SubloteDTOResponse;
import com.perfumeria.aquadebelen.aquadebelen.inventario.viewmodel.ListSubloteViewModel;
import com.perfumeria.aquadebelen.aquadebelen.inventario.viewmodel.SubloteViewModel;

@Component
public class SublotePresenter {

    public SubloteViewModel present(SubloteDTOResponse res) {
        SubloteViewModel svm = new SubloteViewModel();
        svm.setId(String.valueOf(res.id()));
        svm.setCodigoSublote(res.codigoSublote());
        svm.setProducto(res.producto());
        svm.setFechaVencimiento(res.fechaVencimiento());
        svm.setFechaProduccion(res.fechaProduccion());
        svm.setCantidadInicial(String.valueOf(res.cantidadInicial()));
        svm.setCantidadActual(String.valueOf(res.cantidadActual()));
        svm.setCostoUnitario(res.costoUnitario() + " Bs");
        svm.setEstado(res.estado());
        svm.setLoteId("Lote #" + res.loteId());
        return svm;
    }

    public List<ListSubloteViewModel> presentList(List<SubloteDTOResponse> listResp) {
        List<ListSubloteViewModel> lista = new ArrayList<>();
        for (SubloteDTOResponse res : listResp) {
            ListSubloteViewModel svm = new ListSubloteViewModel();
            svm.setCodigoSublote(res.codigoSublote());
            svm.setProducto(res.producto());
            svm.setCantidadActual(String.valueOf(res.cantidadActual()));
            svm.setEstado(res.estado());
            svm.setFechaVencimiento(res.fechaVencimiento());
            lista.add(svm);
        }
        return lista;
    }
}
