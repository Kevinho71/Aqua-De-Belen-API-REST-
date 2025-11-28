package com.perfumeria.aquadebelen.aquadebelen.compras.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
@RequestMapping("/api/v1")
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
    public ResponseEntity<List<ListaCompraViewModel>> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<CompraDTOResponse> resp = compraService.listar(page, size);
        List<ListaCompraViewModel> ltvm = compraPresenter.presentList(resp);
        return ResponseEntity.ok(ltvm);
    }

    @GetMapping("/compras/{id}")
    public ResponseEntity<CompraViewModel> buscar(@PathVariable("id") @Min(1) Integer id) {
        CompraDTOResponse resp = compraService.buscar(id);
        CompraViewModel cvm = compraPresenter.present(resp);
        return ResponseEntity.ok(cvm);
    }

    @GetMapping("/compras/buscar")
    public ResponseEntity<List<ListaCompraViewModel>> buscarPorFiltros(
            @RequestParam(required = false) Integer proveedorId,
            @RequestParam(required = false) String fechaInicio,
            @RequestParam(required = false) String fechaFin) {
        
        java.time.LocalDateTime inicio = fechaInicio != null ? 
            java.time.LocalDateTime.parse(fechaInicio + "T00:00:00") : null;
        java.time.LocalDateTime fin = fechaFin != null ? 
            java.time.LocalDateTime.parse(fechaFin + "T23:59:59") : null;
        
        List<CompraDTOResponse> resp = compraService.buscarPorFiltros(proveedorId, inicio, fin);
        List<ListaCompraViewModel> ltvm = compraPresenter.presentList(resp);
        return ResponseEntity.ok(ltvm);
    }

}
