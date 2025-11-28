package com.perfumeria.aquadebelen.aquadebelen.inventario.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/sublotes/disponibles")
    public ResponseEntity<List<ListSubloteViewModel>> listarDisponibles() {
        List<SubloteDTOResponse> resp = subloteService.findDisponibles();
        List<ListSubloteViewModel> ltvm = sublotePresenter.presentList(resp);
        return ResponseEntity.ok(ltvm);
    }

    @GetMapping("/sublotes/proximos-vencer")
    public ResponseEntity<List<ListSubloteViewModel>> listarProximosAVencer(
            @RequestParam(required = false, defaultValue = "30") Integer dias) {
        List<SubloteDTOResponse> resp = subloteService.findProximosAVencer(dias);
        List<ListSubloteViewModel> ltvm = sublotePresenter.presentList(resp);
        return ResponseEntity.ok(ltvm);
    }

    @GetMapping("/productos/{productoId}/sublotes")
    public ResponseEntity<List<ListSubloteViewModel>> listarPorProducto(
            @PathVariable("productoId") @Min(1) Integer productoId) {
        List<SubloteDTOResponse> resp = subloteService.findByProductoId(productoId);
        List<ListSubloteViewModel> ltvm = sublotePresenter.presentList(resp);
        return ResponseEntity.ok(ltvm);
    }

    @GetMapping("/sublotes/buscar")
    public ResponseEntity<List<ListSubloteViewModel>> buscarPorFiltros(
            @RequestParam(required = false) Integer productoId,
            @RequestParam(required = false) String estado) {
        List<SubloteDTOResponse> resp = subloteService.buscarPorFiltros(productoId, estado);
        List<ListSubloteViewModel> ltvm = sublotePresenter.presentList(resp);
        return ResponseEntity.ok(ltvm);
    }
}
