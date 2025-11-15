package com.perfumeria.aquadebelen.aquadebelen.inventario.presenter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.perfumeria.aquadebelen.aquadebelen.inventario.DTO.ProveedorDTOResponse;
import com.perfumeria.aquadebelen.aquadebelen.inventario.viewmodel.ProveedorViewModel;

@Component
public class ProveedorPresenter {
    
    public ProveedorViewModel present(ProveedorDTOResponse res) {
        ProveedorViewModel pvm = new ProveedorViewModel();
        pvm.setNombre(res.nombre());
        pvm.setCorreo(res.correo());
        pvm.setTelefono(res.telefono());
        pvm.setNit(res.nit());
        pvm.setUbicacion(res.ubicacion());

        return pvm;
    }

    public List<ProveedorViewModel> presentList(List<ProveedorDTOResponse> listResp) {
        List<ProveedorViewModel> lista = new ArrayList<>();
        for (ProveedorDTOResponse res : listResp) {
            ProveedorViewModel pvm = present(res);
            lista.add(pvm);
        }
        return lista;
    }
}
