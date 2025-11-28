package com.perfumeria.aquadebelen.aquadebelen.inventario.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.perfumeria.aquadebelen.aquadebelen.inventario.DTO.LoteDTOResponse;
import com.perfumeria.aquadebelen.aquadebelen.inventario.presenter.LotePresenter;
import com.perfumeria.aquadebelen.aquadebelen.inventario.service.LoteServiceQuery;
import com.perfumeria.aquadebelen.aquadebelen.inventario.viewmodel.ListLoteViewModel;
import com.perfumeria.aquadebelen.aquadebelen.inventario.viewmodel.LoteViewModel;

import jakarta.validation.constraints.Min;

@Validated
@RestController
@RequestMapping("/api/v1")
public class LoteController {

    private final LoteServiceQuery loteService;
    private final LotePresenter lotePresenter;

    public LoteController(LoteServiceQuery loteService, LotePresenter lotePresenter) {
        this.loteService = loteService;
        this.lotePresenter = lotePresenter;
    }

    @GetMapping("/lotes")
    public ResponseEntity<List<ListLoteViewModel>> listar() {
        List<LoteDTOResponse> resp = loteService.listar();
        List<ListLoteViewModel> ltvm = lotePresenter.presentList(resp);
        return ResponseEntity.ok(ltvm);
    }

    @GetMapping("/lotes/{id}")
    public ResponseEntity<LoteViewModel> buscar(@PathVariable("id") @Min(1) Integer id) {
        LoteDTOResponse resp = loteService.buscar(id);
        LoteViewModel lvm = lotePresenter.present(resp);
        return ResponseEntity.ok(lvm);
    }

    @GetMapping("/lotes/buscar")
    public ResponseEntity<List<ListLoteViewModel>> buscarPorFiltros(
            @RequestParam(required = false) Integer compraId,
            @RequestParam(required = false) String fechaInicio,
            @RequestParam(required = false) String fechaFin) {
        LocalDateTime inicio = (fechaInicio != null && !fechaInicio.isEmpty()) 
            ? LocalDateTime.parse(fechaInicio + "T00:00:00") : null;
        LocalDateTime fin = (fechaFin != null && !fechaFin.isEmpty()) 
            ? LocalDateTime.parse(fechaFin + "T23:59:59") : null;
        List<LoteDTOResponse> resp = loteService.buscarPorFiltros(compraId, inicio, fin);
        List<ListLoteViewModel> ltvm = lotePresenter.presentList(resp);
        return ResponseEntity.ok(ltvm);
    }
}
