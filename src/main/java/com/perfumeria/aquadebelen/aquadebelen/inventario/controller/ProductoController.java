package com.perfumeria.aquadebelen.aquadebelen.inventario.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.perfumeria.aquadebelen.aquadebelen.inventario.DTO.ActualizarPrecioRequest;
import com.perfumeria.aquadebelen.aquadebelen.inventario.DTO.ProductoDTORequest;
import com.perfumeria.aquadebelen.aquadebelen.inventario.DTO.ProductoDTOResponse;
import com.perfumeria.aquadebelen.aquadebelen.inventario.presenter.ProductoPresenter;
import com.perfumeria.aquadebelen.aquadebelen.inventario.service.ProductoService;
import com.perfumeria.aquadebelen.aquadebelen.inventario.viewmodel.ListProductoViewModel;
import com.perfumeria.aquadebelen.aquadebelen.inventario.viewmodel.ProductoStockTotalViewModel;
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
    public ResponseEntity<List<ListProductoViewModel>> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<ProductoDTOResponse> resp = productoService.listar(page, size);
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

    @Operation(
        summary = "Actualizar precio de producto",
        description = "Actualiza únicamente el precio de venta de un producto. Crea un nuevo registro en el historial de precios."
    )
    @ApiResponse(responseCode = "200", description = "Precio actualizado exitosamente")
    @PatchMapping("/productos/{id}/precio")
    public ResponseEntity<ProductoViewModel> actualizarPrecio(
        @Parameter(description = "ID del producto", required = true)
        @PathVariable("id") @Min(1) Integer id,
        @Valid @RequestBody ActualizarPrecioRequest req) {
        ProductoDTOResponse resp = productoService.actualizarPrecio(id, req.precioVenta());
        ProductoViewModel pvm = productoPresenter.present(resp);
        return ResponseEntity.ok(pvm);
    }

    @Operation(
        summary = "Eliminar producto",
        description = "Elimina un producto del inventario por su ID"
    )
    @ApiResponse(responseCode = "200", description = "Producto eliminado exitosamente")
    @DeleteMapping("/productos/{id}")
    public ResponseEntity<Void> eliminar(
        @Parameter(description = "ID del producto a eliminar", required = true)
        @PathVariable("id") @Min(1) Integer id) {
        productoService.eliminar(id);
        return ResponseEntity.ok().build();
    }

    @Operation(
        summary = "Contar productos",
        description = "Obtiene el número total de productos registrados en el inventario"
    )
    @ApiResponse(responseCode = "200", description = "Cantidad de productos obtenida")
    @GetMapping("/productos/count")
    public ResponseEntity<Long> contarProductos() {
        Long count = productoService.contarProductos();
        return ResponseEntity.ok(count);
    }

    @Operation(
        summary = "Verificar existencia de producto",
        description = "Verifica si existe un producto con el ID especificado"
    )
    @ApiResponse(responseCode = "200", description = "true si existe, false si no existe")
    @GetMapping("/productos/{id}/exists")
    public ResponseEntity<Boolean> existeProducto(
        @Parameter(description = "ID del producto a verificar", required = true)
        @PathVariable("id") @Min(1) Integer id) {
        boolean existe = productoService.existeProducto(id);
        return ResponseEntity.ok(existe);
    }

    @Operation(
        summary = "Listar stock total por producto",
        description = "Devuelve la lista de productos con la cantidad total disponible sumando todos sus sublotes"
    )
    @ApiResponse(responseCode = "200", description = "Stock total obtenido")
    @GetMapping("/productos/stock-total")
    public ResponseEntity<List<ProductoStockTotalViewModel>> obtenerStockTotalProductos() {
        List<ProductoStockTotalViewModel> stockTotal = productoService.obtenerStockTotalProductos();
        return ResponseEntity.ok(stockTotal);
    }

    @Operation(
        summary = "Obtener stock total de un producto",
        description = "Devuelve la cantidad total disponible de un producto específico sumando todos sus sublotes"
    )
    @ApiResponse(responseCode = "200", description = "Stock total del producto obtenido")
    @GetMapping("/productos/{id}/stock-total")
    public ResponseEntity<ProductoStockTotalViewModel> obtenerStockTotalProducto(
        @Parameter(description = "ID del producto a consultar", required = true)
        @PathVariable("id") @Min(1) Integer id) {
        ProductoStockTotalViewModel stock = productoService.obtenerStockTotalProducto(id);
        return ResponseEntity.ok(stock);
    }

    @Operation(
        summary = "Listar tipos de producto",
        description = "Obtiene la lista de todos los tipos de producto disponibles."
    )
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping("/productos/tipos")
    public ResponseEntity<List<com.perfumeria.aquadebelen.aquadebelen.inventario.viewmodel.TipoProductoViewModel>> listarTiposProducto() {
        return ResponseEntity.ok(productoPresenter.presentTipos(productoService.listarTipos()));
    }


}
