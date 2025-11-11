package com.perfumeria.aquadebelen.aquadebelen.inventario.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.perfumeria.aquadebelen.aquadebelen.inventario.DTO.ProductoDTORequest;
import com.perfumeria.aquadebelen.aquadebelen.inventario.DTO.ProductoDTOResponse;
import com.perfumeria.aquadebelen.aquadebelen.inventario.presenter.ProductoPresenter;
import com.perfumeria.aquadebelen.aquadebelen.inventario.service.ProductoService;
import com.perfumeria.aquadebelen.aquadebelen.inventario.viewmodel.ListProductoViewModel;
import com.perfumeria.aquadebelen.aquadebelen.inventario.viewmodel.ProductoViewModel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@Validated
@RequestMapping("/api/v1")
@RestController
@Tag(name = "Productos", description = "Endpoints para gestión de productos del inventario")
public class ProductoController {


    private final ProductoService productoService;
    private final ProductoPresenter productoPresenter;

   
    
    public ProductoController(ProductoService productoService, ProductoPresenter productoPresenter) {
        this.productoService = productoService;
        this.productoPresenter = productoPresenter;
    }

    @Operation(
        summary = "Crear nuevo producto",
        description = "Registra un nuevo producto en el inventario"
    )
    @ApiResponse(responseCode = "200", description = "Producto creado exitosamente")
    @PostMapping("/productos")
    public ResponseEntity<ProductoViewModel> registrar(@Valid @RequestBody ProductoDTORequest req) {
        ProductoDTOResponse resp = productoService.store(null, req);
        ProductoViewModel pvm = productoPresenter.present(resp);
        return ResponseEntity.ok(pvm);  
    }

    @Operation(
        summary = "Actualizar producto existente",
        description = "Modifica los datos de un producto existente por su ID"
    )
    @PutMapping("/productos/{id}")
    public ResponseEntity<ProductoViewModel> editar(@Valid @RequestBody ProductoDTORequest req, @PathVariable("id") @Min(1) Integer id) {
        ProductoDTOResponse resp = productoService.store(id, req);
        ProductoViewModel pvm = productoPresenter.present(resp);

        return ResponseEntity.ok(pvm);
    }

    @Operation(
        summary = "Listar todos los productos",
        description = "Obtiene la lista completa de productos registrados en el inventario"
    )
    @GetMapping("/productos")
    public ResponseEntity<List<ListProductoViewModel>> listar() {
        List<ProductoDTOResponse> resp = productoService.listar();
        List<ListProductoViewModel> ltvm = productoPresenter.presentList(resp);
        return ResponseEntity.ok(ltvm);
    }

    @Operation(
        summary = "Buscar producto por ID",
        description = "Obtiene los detalles de un producto específico mediante su ID"
    )
    @GetMapping("/productos/{id}")
    public ResponseEntity<ProductoViewModel> buscar(
        @Parameter(description = "ID del producto a buscar", required = true)
        @PathVariable("id") @Min(1) Integer id) {
        ProductoDTOResponse resp = productoService.buscar(id);
       ProductoViewModel pvm = productoPresenter.present(resp);
        return ResponseEntity.ok(pvm);
    }

    @Operation(
        summary = "Buscar productos con filtros",
        description = "Busca productos por nombre (parcial) y/o tipo de producto. Todos los parámetros son opcionales."
    )
    @GetMapping("/productos/buscar")
    public ResponseEntity<List<ListProductoViewModel>> buscarPorNombre(
        @Parameter(description = "Nombre del producto (búsqueda parcial, case-insensitive)")
        @RequestParam(required = false) String nombre,
        @Parameter(description = "ID del tipo de producto")
        @RequestParam(required = false) Integer tipoProductoId) {
        List<ProductoDTOResponse> resp = productoService.buscarPorFiltros(nombre, tipoProductoId);
        List<ListProductoViewModel> ltvm = productoPresenter.presentList(resp);
        return ResponseEntity.ok(ltvm);
    }


}
