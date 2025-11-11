package com.perfumeria.aquadebelen.aquadebelen.inventario.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perfumeria.aquadebelen.aquadebelen.inventario.DTO.SubloteDTOResponse;
import com.perfumeria.aquadebelen.aquadebelen.inventario.presenter.SublotePresenter;
import com.perfumeria.aquadebelen.aquadebelen.inventario.service.SubloteServiceQuery;
import com.perfumeria.aquadebelen.aquadebelen.inventario.viewmodel.ListSubloteViewModel;
import com.perfumeria.aquadebelen.aquadebelen.inventario.viewmodel.SubloteViewModel;

import jakarta.validation.constraints.Min;

@Validated
@RestController
@RequestMapping("/api/v1")
public class SubloteController {

    private final SubloteServiceQuery subloteService;
    private final SublotePresenter sublotePresenter;

    public SubloteController(SubloteServiceQuery subloteService, SublotePresenter sublotePresenter) {
        this.subloteService = subloteService;
        this.sublotePresenter = sublotePresenter;
    }

    @GetMapping("/sublotes")
    public ResponseEntity<List<ListSubloteViewModel>> listar() {
        List<SubloteDTOResponse> resp = subloteService.listar();
        List<ListSubloteViewModel> ltvm = sublotePresenter.presentList(resp);
        return ResponseEntity.ok(ltvm);
    }

    @GetMapping("/sublotes/{id}")
    public ResponseEntity<SubloteViewModel> buscar(@PathVariable("id") @Min(1) Integer id) {
        SubloteDTOResponse resp = subloteService.buscar(id);
        SubloteViewModel svm = sublotePresenter.present(resp);
        return ResponseEntity.ok(svm);
    }
}
