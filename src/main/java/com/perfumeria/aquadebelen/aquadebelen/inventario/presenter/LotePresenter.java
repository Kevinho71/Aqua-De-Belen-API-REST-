package com.perfumeria.aquadebelen.aquadebelen.inventario.presenter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.perfumeria.aquadebelen.aquadebelen.inventario.DTO.LoteDTOResponse;
import com.perfumeria.aquadebelen.aquadebelen.inventario.viewmodel.ListLoteViewModel;
import com.perfumeria.aquadebelen.aquadebelen.inventario.viewmodel.LoteViewModel;

@Component
public class LotePresenter {

    public LoteViewModel present(LoteDTOResponse res) {
        LoteViewModel lvm = new LoteViewModel();
        lvm.setId(res.id() != null ? String.valueOf(res.id()) : "N/A");
        lvm.setIdRaw(res.id());
        lvm.setFechaIngreso(res.fechaIngreso());
        Integer compraId = res.compraId();
        lvm.setCompraId(compraId != null ? "Compra #" + compraId : "Sin compra");
        lvm.setCompraIdRaw(compraId);
        Integer cantidadSublotes = res.cantidadSublotes();
        lvm.setCantidadSublotes(cantidadSublotes + " sublotes");
        lvm.setCantidadSublotesRaw(cantidadSublotes);
        return lvm;
    }

    public List<ListLoteViewModel> presentList(List<LoteDTOResponse> listResp) {
        List<ListLoteViewModel> lista = new ArrayList<>();
        for (LoteDTOResponse res : listResp) {
            ListLoteViewModel lvm = new ListLoteViewModel();
            lvm.setId(res.id());
            lvm.setFechaIngreso(res.fechaIngreso());
            Integer compraId = res.compraId();
            lvm.setCompraId(compraId != null ? "Compra #" + compraId : "Sin compra");
            lvm.setCompraIdRaw(compraId);
            Integer cantidadSublotes = res.cantidadSublotes();
            lvm.setCantidadSublotes(cantidadSublotes + " sublotes");
            lvm.setCantidadSublotesRaw(cantidadSublotes);
            lista.add(lvm);
        }
        return lista;
    }
}
