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
        pvm.setId(res.id());
        pvm.setNombre(res.nombre());
        pvm.setCorreo(res.correo());
        pvm.setTelefono(res.telefono());
        pvm.setNit(res.nit());
        pvm.setUbicacionId(res.ubicacionId());
        pvm.setCiudad(res.ubicacionCiudad());
        pvm.setZona(res.ubicacionZona());
        pvm.setUbicacion(buildUbicacionLabel(res.ubicacionCiudad(), res.ubicacionZona()));

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

    private String buildUbicacionLabel(String ciudad, String zona) {
        if (ciudad == null && zona == null) {
            return null;
        }
        if (zona == null || zona.isBlank()) {
            return ciudad;
        }
        if (ciudad == null || ciudad.isBlank()) {
            return zona;
        }
        return ciudad + " - " + zona;
    }
}
