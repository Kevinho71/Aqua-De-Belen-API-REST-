package com.perfumeria.aquadebelen.aquadebelen.inventario.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.perfumeria.aquadebelen.aquadebelen.inventario.model.PedidoSugerido;
import com.perfumeria.aquadebelen.aquadebelen.inventario.model.PedidoSugerido.EstadoPedidoSugerido;
import com.perfumeria.aquadebelen.aquadebelen.inventario.repository.PedidoSugeridoDAO;
import com.perfumeria.aquadebelen.aquadebelen.inventario.service.PedidoAutomaticoService;
import com.perfumeria.aquadebelen.aquadebelen.inventario.viewmodel.PedidoSugeridoViewModel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Pedidos Sugeridos", description = "Gestión de pedidos automáticos generados por el sistema")
public class PedidoSugeridoController {

    private final PedidoSugeridoDAO pedidoSugeridoDAO;
    private final PedidoAutomaticoService pedidoAutomaticoService;

    public PedidoSugeridoController(PedidoSugeridoDAO pedidoSugeridoDAO, 
                                   PedidoAutomaticoService pedidoAutomaticoService) {
        this.pedidoSugeridoDAO = pedidoSugeridoDAO;
        this.pedidoAutomaticoService = pedidoAutomaticoService;
    }

    @Operation(summary = "Listar todos los pedidos sugeridos")
    @GetMapping("/pedidos-sugeridos")
    public ResponseEntity<List<PedidoSugeridoViewModel>> listar() {
        List<PedidoSugerido> pedidos = pedidoSugeridoDAO.findAll();
        List<PedidoSugeridoViewModel> viewModels = pedidos.stream()
            .map(this::toViewModel)
            .collect(Collectors.toList());
        return ResponseEntity.ok(viewModels);
    }

    @Operation(summary = "Obtener pedido sugerido por ID")
    @GetMapping("/pedidos-sugeridos/{id}")
    public ResponseEntity<PedidoSugeridoViewModel> obtenerPorId(@PathVariable Integer id) {
        PedidoSugerido pedido = pedidoSugeridoDAO.findById(id);
        if (pedido == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(toViewModel(pedido));
    }

    @Operation(summary = "Listar pedidos sugeridos por estado")
    @GetMapping("/pedidos-sugeridos/estado/{estado}")
    public ResponseEntity<List<PedidoSugeridoViewModel>> listarPorEstado(@PathVariable EstadoPedidoSugerido estado) {
        List<PedidoSugerido> pedidos = pedidoSugeridoDAO.findByEstado(estado);
        List<PedidoSugeridoViewModel> viewModels = pedidos.stream()
            .map(this::toViewModel)
            .collect(Collectors.toList());
        return ResponseEntity.ok(viewModels);
    }

    @Operation(summary = "Listar pedidos sugeridos por producto")
    @GetMapping("/pedidos-sugeridos/producto/{productoId}")
    public ResponseEntity<List<PedidoSugeridoViewModel>> listarPorProducto(@PathVariable Integer productoId) {
        List<PedidoSugerido> pedidos = pedidoSugeridoDAO.findByProductoId(productoId);
        List<PedidoSugeridoViewModel> viewModels = pedidos.stream()
            .map(this::toViewModel)
            .collect(Collectors.toList());
        return ResponseEntity.ok(viewModels);
    }

    @Operation(summary = "Actualizar estado de pedido sugerido")
    @PatchMapping("/pedidos-sugeridos/{id}/estado")
    public ResponseEntity<PedidoSugeridoViewModel> actualizarEstado(
            @PathVariable Integer id,
            @RequestParam EstadoPedidoSugerido estado) {
        
        PedidoSugerido pedido = pedidoSugeridoDAO.findById(id);
        if (pedido == null) {
            return ResponseEntity.notFound().build();
        }
        
        pedido.setEstado(estado);
        pedidoSugeridoDAO.store(pedido);
        
        return ResponseEntity.ok(toViewModel(pedido));
    }

    @Operation(summary = "Ejecutar generación manual de pedidos")
    @GetMapping("/pedidos-sugeridos/generar-ahora")
    public ResponseEntity<String> generarPedidosManualmente() {
        int cantidad = pedidoAutomaticoService.generarPedidosAutomaticos();
        return ResponseEntity.ok(String.format("Se generaron %d pedidos sugeridos", cantidad));
    }

    private PedidoSugeridoViewModel toViewModel(PedidoSugerido pedido) {
        return new PedidoSugeridoViewModel(
            pedido.getId(),
            pedido.getProducto().getId(),
            pedido.getProducto().getNombre(),
            pedido.getCantidadSugerida(),
            pedido.getFechaSugerida(),
            pedido.getEstado().name(),
            pedido.getObservacion(),
            pedido.getStockActualMomento(),
            pedido.getRopMomento()
        );
    }
}
