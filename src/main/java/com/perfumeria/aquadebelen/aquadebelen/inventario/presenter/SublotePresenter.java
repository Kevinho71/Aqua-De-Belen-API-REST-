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
        svm.setId(res.id() != null ? String.valueOf(res.id()) : "N/A");
        svm.setIdRaw(res.id());
        svm.setCodigoSublote(res.codigoSublote());
        svm.setProducto(res.producto());
        svm.setFechaVencimiento(res.fechaVencimiento());
        svm.setFechaProduccion(res.fechaProduccion());
        svm.setCantidadInicial(res.cantidadInicial() != null ? String.valueOf(res.cantidadInicial()) : "0");
        svm.setCantidadInicialRaw(res.cantidadInicial());
        svm.setCantidadActual(res.cantidadActual() != null ? String.valueOf(res.cantidadActual()) : "0");
        svm.setCantidadActualRaw(res.cantidadActual());
        if (res.costoUnitario() != null) {
            svm.setCostoUnitario(res.costoUnitario() + " Bs");
            svm.setCostoUnitarioRaw(res.costoUnitario());
        } else {
            svm.setCostoUnitario("N/A");
            svm.setCostoUnitarioRaw(null);
        }
        svm.setEstado(res.estado());
        Integer loteId = res.loteId();
        svm.setLoteId(loteId != null ? "Lote #" + loteId : "Sin lote");
        svm.setLoteIdRaw(loteId);
        return svm;
    }

    public List<ListSubloteViewModel> presentList(List<SubloteDTOResponse> listResp) {
        List<ListSubloteViewModel> lista = new ArrayList<>();
        for (SubloteDTOResponse res : listResp) {
            ListSubloteViewModel svm = new ListSubloteViewModel();
            svm.setId(res.id());
            svm.setCodigoSublote(res.codigoSublote());
            svm.setProducto(res.producto());
            svm.setCantidadActual(res.cantidadActual() != null ? String.valueOf(res.cantidadActual()) : "0");
            svm.setEstado(res.estado());
            svm.setFechaVencimiento(res.fechaVencimiento());
            svm.setLoteId(res.loteId());
            lista.add(svm);
        }
        return lista;
    }
}
