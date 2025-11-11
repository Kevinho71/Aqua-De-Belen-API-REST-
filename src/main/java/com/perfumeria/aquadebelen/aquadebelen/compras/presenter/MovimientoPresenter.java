package com.perfumeria.aquadebelen.aquadebelen.compras.presenter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.perfumeria.aquadebelen.aquadebelen.compras.DTO.MovimientoDTOResponse;
import com.perfumeria.aquadebelen.aquadebelen.compras.viewmodel.ListMovimientoViewModel;
import com.perfumeria.aquadebelen.aquadebelen.compras.viewmodel.MovimientoViewModel;

@Component
public class MovimientoPresenter {

    public MovimientoViewModel present(MovimientoDTOResponse res) {
        MovimientoViewModel mvm = new MovimientoViewModel();
        mvm.setId(String.valueOf(res.id()));
        mvm.setCantidad(String.valueOf(res.cantidad()));
        mvm.setCostoUnitario(res.costoUnitario() + " Bs");
        mvm.setPrecioUnitario(res.precioUnitario() + " Bs");
        mvm.setCostoTotal(res.costoTotal() + " Bs");
        mvm.setPrecioTotal(res.precioTotal() + " Bs");
        mvm.setFecha(res.fecha());
        mvm.setTipo(res.referenciaTipo());
        mvm.setReferencia(res.referenciaTipo() + " #" + res.referenciaId());
        return mvm;
    }

    public List<ListMovimientoViewModel> presentList(List<MovimientoDTOResponse> listResp) {
        List<ListMovimientoViewModel> lista = new ArrayList<>();
        for (MovimientoDTOResponse res : listResp) {
            ListMovimientoViewModel mvm = new ListMovimientoViewModel();
            mvm.setFecha(res.fecha());
            mvm.setTipo(res.referenciaTipo());
            mvm.setCantidad(String.valueOf(res.cantidad()));
            mvm.setReferencia(res.referenciaTipo() + " #" + res.referenciaId());
            lista.add(mvm);
        }
        return lista;
    }
}
