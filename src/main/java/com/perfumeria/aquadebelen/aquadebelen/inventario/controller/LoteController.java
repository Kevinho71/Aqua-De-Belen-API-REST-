package com.perfumeria.aquadebelen.aquadebelen.inventario.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
