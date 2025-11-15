package com.perfumeria.aquadebelen.aquadebelen.ventas.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perfumeria.aquadebelen.aquadebelen.ventas.DTO.VentaRequest;
import com.perfumeria.aquadebelen.aquadebelen.ventas.DTO.VentaResponse;
import com.perfumeria.aquadebelen.aquadebelen.ventas.presenter.VentaPresenter;
import com.perfumeria.aquadebelen.aquadebelen.ventas.service.VentaService;
import com.perfumeria.aquadebelen.aquadebelen.ventas.viewmodel.ListVentaViewModel;
import com.perfumeria.aquadebelen.aquadebelen.ventas.viewmodel.VentaViewModel;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Validated
@RestController
@RequestMapping("/api/v1")
public class VentasController {

    private final VentaService VentaService;
    private final VentaPresenter VentaPresenter;

    public VentasController(VentaService VentaService, VentaPresenter VentaPresenter) {
        this.VentaService = VentaService;
        this.VentaPresenter = VentaPresenter;
    }

    @PostMapping("/ventas")
    public ResponseEntity<VentaViewModel> registrar(@Valid @RequestBody VentaRequest req) {
        VentaResponse resp = VentaService.store(null, req);
        VentaViewModel tvm = VentaPresenter.present(resp);
        return ResponseEntity.ok(tvm);  
    }

    @PutMapping("/ventas/{id}")
    public ResponseEntity<VentaViewModel> editar(@Valid @RequestBody VentaRequest req, @PathVariable("id") @Min(1) Integer id) {
        VentaResponse resp = VentaService.store(id, req);
        VentaViewModel tvm = VentaPresenter.present(resp);

        return ResponseEntity.ok(tvm);
    }

    @DeleteMapping("/ventas/{id}")
    public void borrar(@PathVariable("id") @Min(1) Integer id) {
        VentaService.borrar(id);
    }

    @GetMapping("/ventas")
    public ResponseEntity<List<ListVentaViewModel>> listar() {
        List<VentaResponse> resp = VentaService.listar();
        List<ListVentaViewModel> ltvm = VentaPresenter.presentList(resp);
        return ResponseEntity.ok(ltvm);
    }

    @GetMapping("/ventas/{id}")
    public ResponseEntity<VentaViewModel> buscar(@PathVariable("id") @Min(1) Integer id) {
        VentaResponse resp = VentaService.buscar(id);
       VentaViewModel tvm = VentaPresenter.present(resp);
        return ResponseEntity.ok(tvm);
    }


}
