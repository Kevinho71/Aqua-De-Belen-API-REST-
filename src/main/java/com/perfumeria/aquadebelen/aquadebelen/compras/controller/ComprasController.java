/*package com.perfumeria.aquadebelen.aquadebelen.compras.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.perfumeria.aquadebelen.aquadebelen.compras.DTO.CompraDTORequest;
import com.perfumeria.aquadebelen.aquadebelen.compras.DTO.CompraDTOResponse;
import com.perfumeria.aquadebelen.aquadebelen.compras.presenter.CompraPresenter;
import com.perfumeria.aquadebelen.aquadebelen.compras.service.CompraService;
import com.perfumeria.aquadebelen.aquadebelen.compras.viewmodel.CompraViewModel;
import com.perfumeria.aquadebelen.aquadebelen.compras.viewmodel.ListaCompraViewModel;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@Validated
@RestController
public class ComprasController {

    private final CompraService compraService;
    private final CompraPresenter compraPresenter;

    public ComprasController(CompraService compraService, CompraPresenter compraPresenter) {
        this.compraService = compraService;
        this.compraPresenter = compraPresenter;
    }

    @PostMapping("/compras")
    public ResponseEntity<CompraViewModel> registrar(@Valid @RequestBody CompraDTORequest req) {
        CompraDTOResponse resp = compraService.store(null, req);
        CompraViewModel cvm = compraPresenter.present(resp);
        return ResponseEntity.ok(cvm);
    }

    @PutMapping("/compras/{id}")
    public ResponseEntity<CompraViewModel> editar(@Valid @RequestBody CompraDTORequest req,
            @PathVariable("id") @Min(1) Integer id) {
        CompraDTOResponse resp = compraService.store(id, req);
        CompraViewModel cvm = compraPresenter.present(resp);

        return ResponseEntity.ok(cvm);
    }

    @DeleteMapping("/compras/{id}")
    public void borrar(@PathVariable("id") @Min(1) Integer id) {
        compraService.borrar(id);
    }

    @GetMapping("/compras")
    public ResponseEntity<List<ListaCompraViewModel>> listar() {
        List<CompraDTOResponse> resp = compraService.listar();
        List<ListaCompraViewModel> ltvm = compraPresenter.presentList(resp);
        return ResponseEntity.ok(ltvm);
    }

    @GetMapping("/compras/{id}")
    public ResponseEntity<CompraViewModel> buscar(@PathVariable("id") @Min(1) Integer id) {
        CompraDTOResponse resp = compraService.buscar(id);
        CompraViewModel cvm = compraPresenter.present(resp);
        return ResponseEntity.ok(cvm);
    }

}
*/