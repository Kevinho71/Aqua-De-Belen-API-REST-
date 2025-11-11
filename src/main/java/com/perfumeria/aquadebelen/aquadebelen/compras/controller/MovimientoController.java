package com.perfumeria.aquadebelen.aquadebelen.compras.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perfumeria.aquadebelen.aquadebelen.compras.DTO.MovimientoDTOResponse;
import com.perfumeria.aquadebelen.aquadebelen.compras.presenter.MovimientoPresenter;
import com.perfumeria.aquadebelen.aquadebelen.compras.service.MovimientoServiceQuery;
import com.perfumeria.aquadebelen.aquadebelen.compras.viewmodel.ListMovimientoViewModel;
import com.perfumeria.aquadebelen.aquadebelen.compras.viewmodel.MovimientoViewModel;

import jakarta.validation.constraints.Min;

@Validated
@RestController
@RequestMapping("/api/v1")
public class MovimientoController {

    private final MovimientoServiceQuery movimientoService;
    private final MovimientoPresenter movimientoPresenter;

    public MovimientoController(MovimientoServiceQuery movimientoService, MovimientoPresenter movimientoPresenter) {
        this.movimientoService = movimientoService;
        this.movimientoPresenter = movimientoPresenter;
    }

    @GetMapping("/movimientos")
    public ResponseEntity<List<ListMovimientoViewModel>> listar() {
        List<MovimientoDTOResponse> resp = movimientoService.listar();
        List<ListMovimientoViewModel> ltvm = movimientoPresenter.presentList(resp);
        return ResponseEntity.ok(ltvm);
    }

    @GetMapping("/movimientos/{id}")
    public ResponseEntity<MovimientoViewModel> buscar(@PathVariable("id") @Min(1) Integer id) {
        MovimientoDTOResponse resp = movimientoService.buscar(id);
        MovimientoViewModel mvm = movimientoPresenter.present(resp);
        return ResponseEntity.ok(mvm);
    }
}
