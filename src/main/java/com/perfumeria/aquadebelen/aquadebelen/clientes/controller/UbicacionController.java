package com.perfumeria.aquadebelen.aquadebelen.clientes.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perfumeria.aquadebelen.aquadebelen.clientes.model.Ubicacion;
import com.perfumeria.aquadebelen.aquadebelen.clientes.presenter.UbicacionPresenter;
import com.perfumeria.aquadebelen.aquadebelen.clientes.service.UbicacionService;
import com.perfumeria.aquadebelen.aquadebelen.clientes.viewmodel.UbicacionViewModel;

@RestController
@RequestMapping("/api/v1")
public class UbicacionController {

    private final UbicacionService ubicacionService;
    private final UbicacionPresenter ubicacionPresenter;

    public UbicacionController(UbicacionService ubicacionService, UbicacionPresenter ubicacionPresenter) {
        this.ubicacionService = ubicacionService;
        this.ubicacionPresenter = ubicacionPresenter;
    }

    @GetMapping("/ubicaciones")
    public ResponseEntity<List<UbicacionViewModel>> listar() {
        List<Ubicacion> ubicaciones = ubicacionService.listar();
        return ResponseEntity.ok(ubicacionPresenter.presentList(ubicaciones));
    }
}
