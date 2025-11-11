package com.perfumeria.aquadebelen.aquadebelen.inventario.presenter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.perfumeria.aquadebelen.aquadebelen.inventario.DTO.LoteDTOResponse;
import com.perfumeria.aquadebelen.aquadebelen.inventario.viewmodel.ListLoteViewModel;
import com.perfumeria.aquadebelen.aquadebelen.inventario.viewmodel.LoteViewModel;

@Component
public class LotePresenter {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public LoteViewModel present(LoteDTOResponse res) {
        LoteViewModel lvm = new LoteViewModel();
        lvm.setId(String.valueOf(res.id()));
        lvm.setFechaIngreso(res.fechaIngreso());
        lvm.setCompraId("Compra #" + res.compraId());
        lvm.setCantidadSublotes(res.cantidadSublotes() + " sublotes");
        return lvm;
    }

    public List<ListLoteViewModel> presentList(List<LoteDTOResponse> listResp) {
        List<ListLoteViewModel> lista = new ArrayList<>();
        for (LoteDTOResponse res : listResp) {
            ListLoteViewModel lvm = new ListLoteViewModel();
            lvm.setFechaIngreso(res.fechaIngreso());
            lvm.setCompraId("Compra #" + res.compraId());
            lvm.setCantidadSublotes(res.cantidadSublotes() + " sublotes");
            lista.add(lvm);
        }
        return lista;
    }
}
