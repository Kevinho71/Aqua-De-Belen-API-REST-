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

import com.perfumeria.aquadebelen.aquadebelen.inventario.DTO.ProductoDTORequest;
import com.perfumeria.aquadebelen.aquadebelen.inventario.DTO.ProductoDTOResponse;
import com.perfumeria.aquadebelen.aquadebelen.inventario.presenter.ProductoPresenter;
import com.perfumeria.aquadebelen.aquadebelen.inventario.service.ProductoService;
import com.perfumeria.aquadebelen.aquadebelen.inventario.viewmodel.ListProductoViewModel;
import com.perfumeria.aquadebelen.aquadebelen.inventario.viewmodel.ProductoViewModel;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@Validated
@RequestMapping("/api/v1")
@RestController
public class ProductoController {


    private final ProductoService productoService;
    private final ProductoPresenter productoPresenter;

   
    
    public ProductoController(ProductoService productoService, ProductoPresenter productoPresenter) {
        this.productoService = productoService;
        this.productoPresenter = productoPresenter;
    }

    @PostMapping("/productos")
    public ResponseEntity<ProductoViewModel> registrar(@Valid @RequestBody ProductoDTORequest req) {
        ProductoDTOResponse resp = productoService.store(null, req);
        ProductoViewModel pvm = productoPresenter.present(resp);
        return ResponseEntity.ok(pvm);  
    }

    @PutMapping("/productos/{id}")
    public ResponseEntity<ProductoViewModel> editar(@Valid @RequestBody ProductoDTORequest req, @PathVariable("id") @Min(1) Integer id) {
        ProductoDTOResponse resp = productoService.store(id, req);
        ProductoViewModel pvm = productoPresenter.present(resp);

        return ResponseEntity.ok(pvm);
    }

    @GetMapping("/productos")
    public ResponseEntity<List<ListProductoViewModel>> listar() {
        List<ProductoDTOResponse> resp = productoService.listar();
        List<ListProductoViewModel> ltvm = productoPresenter.presentList(resp);
        return ResponseEntity.ok(ltvm);
    }

    @GetMapping("/productos/{id}")
    public ResponseEntity<ProductoViewModel> buscar(@PathVariable("id") @Min(1) Integer id) {
        ProductoDTOResponse resp = productoService.buscar(id);
       ProductoViewModel pvm = productoPresenter.present(resp);
        return ResponseEntity.ok(pvm);
    }


}
