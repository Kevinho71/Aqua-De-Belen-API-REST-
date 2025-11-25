package com.perfumeria.aquadebelen.aquadebelen.clientes.presenter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.perfumeria.aquadebelen.aquadebelen.clientes.DTO.ClienteDTOResponse;
import com.perfumeria.aquadebelen.aquadebelen.clientes.viewmodel.ClienteViewModel;
import com.perfumeria.aquadebelen.aquadebelen.clientes.viewmodel.ListClienteViewModel;

@Component
public class ClientePresenter {

    public ClienteViewModel present(ClienteDTOResponse res) {
        ClienteViewModel cvm = new ClienteViewModel();
        cvm.setId(String.valueOf(res.id()));
        cvm.setNombre(res.nombre());
        cvm.setApellido(res.apellido());
        cvm.setNombreCompleto(res.nombre() + " " + res.apellido());
        cvm.setTelefono(res.telefono() != null ? res.telefono() : "Sin teléfono");
        cvm.setNitCi(res.nitCi() != null ? res.nitCi() : "Sin NIT/CI");
        cvm.setDireccion(res.direccion() != null ? res.direccion() : "Sin dirección");
        cvm.setNivelFidelidad(res.nivelFidelidad());
        cvm.setNivelFidelidadId(res.nivelFidelidadId());
        cvm.setUbicacion(res.ubicacion());
        cvm.setUbicacionId(res.ubicacionId());
        return cvm;
    }

    public List<ListClienteViewModel> presentList(List<ClienteDTOResponse> listResp) {
        List<ListClienteViewModel> lista = new ArrayList<>();
        for (ClienteDTOResponse res : listResp) {
            ListClienteViewModel cvm = new ListClienteViewModel();
            cvm.setId(res.id());
            cvm.setNombre(res.nombre());
            cvm.setApellido(res.apellido());
            cvm.setNombreCompleto(res.nombre() + " " + res.apellido());
            cvm.setTelefono(res.telefono() != null ? res.telefono() : "Sin teléfono");
            cvm.setNitCi(res.nitCi() != null ? res.nitCi() : "Sin NIT/CI");
            cvm.setDireccion(res.direccion() != null ? res.direccion() : "Sin dirección");
            cvm.setNivelFidelidad(res.nivelFidelidad());
            cvm.setNivelFidelidadId(res.nivelFidelidadId());
            cvm.setUbicacion(res.ubicacion());
            cvm.setUbicacionId(res.ubicacionId());
            lista.add(cvm);
        }
        return lista;
    }
}
