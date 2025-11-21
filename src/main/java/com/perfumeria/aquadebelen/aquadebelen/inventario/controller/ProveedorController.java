package com.perfumeria.aquadebelen.aquadebelen.inventario.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.perfumeria.aquadebelen.aquadebelen.inventario.DTO.ProveedorDTORequest;
import com.perfumeria.aquadebelen.aquadebelen.inventario.DTO.ProveedorDTOResponse;
import com.perfumeria.aquadebelen.aquadebelen.inventario.presenter.ProveedorPresenter;
import com.perfumeria.aquadebelen.aquadebelen.inventario.service.ProveedorService;
import com.perfumeria.aquadebelen.aquadebelen.inventario.viewmodel.ProveedorViewModel;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@Validated
@RestController
@RequestMapping("/api/v1")
public class ProveedorController {

    private final ProveedorService proveedorService;
    private final ProveedorPresenter proveedorPresenter;

    public ProveedorController(ProveedorService proveedorService, ProveedorPresenter proveedorPresenter) {
        this.proveedorService = proveedorService;
        this.proveedorPresenter = proveedorPresenter;
    }

    @PostMapping("/proveedor")
    public ResponseEntity<ProveedorViewModel> registrar(@Valid @RequestBody ProveedorDTORequest req) {
        ProveedorDTOResponse resp = proveedorService.store(null, req);
        ProveedorViewModel pvm = proveedorPresenter.present(resp);
        return ResponseEntity.ok(pvm);
    }

    @PutMapping("/proveedor/{id}")
    public ResponseEntity<ProveedorViewModel> editar(@Valid @RequestBody ProveedorDTORequest req,
            @PathVariable("id") @Min(1) Integer id) {
        ProveedorDTOResponse resp = proveedorService.store(id, req);
        ProveedorViewModel pvm = proveedorPresenter.present(resp);

        return ResponseEntity.ok(pvm);
    }


    @GetMapping("/proveedor")
    public ResponseEntity<List<ProveedorViewModel>> listar() {
        List<ProveedorDTOResponse> resp = proveedorService.listar();
        List<ProveedorViewModel> lpvm = proveedorPresenter.presentList(resp);
        return ResponseEntity.ok(lpvm);
    }

    @GetMapping("/proveedor/{id}")
    public ResponseEntity<ProveedorViewModel> buscar(@PathVariable("id") @Min(1) Integer id) {
        ProveedorDTOResponse resp = proveedorService.buscar(id);
        ProveedorViewModel pvm = proveedorPresenter.present(resp);
        return ResponseEntity.ok(pvm);
    }

    @DeleteMapping("/proveedor/{id}")
    public ResponseEntity<Void> borrar(@PathVariable("id") @Min(1) Integer id) {
        proveedorService.borrar(id);
        return ResponseEntity.noContent().build();
    }
}
