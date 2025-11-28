package com.perfumeria.aquadebelen.aquadebelen.clientes.presenter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.perfumeria.aquadebelen.aquadebelen.clientes.model.Ubicacion;
import com.perfumeria.aquadebelen.aquadebelen.clientes.viewmodel.UbicacionViewModel;

@Component
public class UbicacionPresenter {
    
    public UbicacionViewModel present(Ubicacion entity) {
        return new UbicacionViewModel(
            entity.getId(),
            entity.getCiudad(),
            entity.getZona(),
            entity.getCiudad() + " - " + entity.getZona()
        );
    }

    public List<UbicacionViewModel> presentList(List<Ubicacion> entities) {
        return entities.stream()
            .map(this::present)
            .collect(Collectors.toList());
    }
}
