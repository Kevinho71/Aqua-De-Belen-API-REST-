package com.perfumeria.aquadebelen.aquadebelen.compras.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity<List<ListMovimientoViewModel>> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<MovimientoDTOResponse> resp = movimientoService.listar(page, size);
        List<ListMovimientoViewModel> ltvm = movimientoPresenter.presentList(resp);
        return ResponseEntity.ok(ltvm);
    }

    @GetMapping("/movimientos/{id}")
    public ResponseEntity<MovimientoViewModel> buscar(@PathVariable("id") @Min(1) Integer id) {
        MovimientoDTOResponse resp = movimientoService.buscar(id);
        MovimientoViewModel mvm = movimientoPresenter.present(resp);
        return ResponseEntity.ok(mvm);
    }

    @GetMapping("/movimientos/buscar")
    public ResponseEntity<List<ListMovimientoViewModel>> buscarPorFiltros(
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) String fechaInicio,
            @RequestParam(required = false) String fechaFin,
            @RequestParam(required = false) Integer subloteId) {
        LocalDateTime inicio = (fechaInicio != null && !fechaInicio.isEmpty()) 
            ? LocalDateTime.parse(fechaInicio + "T00:00:00") : null;
        LocalDateTime fin = (fechaFin != null && !fechaFin.isEmpty()) 
            ? LocalDateTime.parse(fechaFin + "T23:59:59") : null;
        List<MovimientoDTOResponse> resp = movimientoService.buscarPorFiltros(tipo, inicio, fin, subloteId);
        List<ListMovimientoViewModel> ltvm = movimientoPresenter.presentList(resp);
        return ResponseEntity.ok(ltvm);
    }
}
