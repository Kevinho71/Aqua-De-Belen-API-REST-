package com.perfumeria.aquadebelen.aquadebelen.clientes.controller;

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

import com.perfumeria.aquadebelen.aquadebelen.clientes.DTO.ClienteDTORequest;
import com.perfumeria.aquadebelen.aquadebelen.clientes.DTO.ClienteDTOResponse;
import com.perfumeria.aquadebelen.aquadebelen.clientes.presenter.ClientePresenter;
import com.perfumeria.aquadebelen.aquadebelen.clientes.service.ClienteService;
import com.perfumeria.aquadebelen.aquadebelen.clientes.viewmodel.ClienteViewModel;
import com.perfumeria.aquadebelen.aquadebelen.clientes.viewmodel.ListClienteViewModel;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@Validated
@RestController
@RequestMapping("/api/v1")
public class ClienteController {

    private final ClienteService clienteService;
    private final ClientePresenter clientePresenter;

    public ClienteController(ClienteService clienteService, ClientePresenter clientePresenter) {
        this.clienteService = clienteService;
        this.clientePresenter = clientePresenter;
    }

    @PostMapping("/clientes")
    public ResponseEntity<ClienteViewModel> registrar(@Valid @RequestBody ClienteDTORequest req) {
        ClienteDTOResponse resp = clienteService.store(null, req);
        ClienteViewModel cvm = clientePresenter.present(resp);
        return ResponseEntity.ok(cvm);
    }

    @PutMapping("/clientes/{id}")
    public ResponseEntity<ClienteViewModel> editar(@Valid @RequestBody ClienteDTORequest req,
            @PathVariable("id") @Min(1) Integer id) {
        ClienteDTOResponse resp = clienteService.store(id, req);
        ClienteViewModel cvm = clientePresenter.present(resp);
        return ResponseEntity.ok(cvm);
    }

    @DeleteMapping("/clientes/{id}")
    public void borrar(@PathVariable("id") @Min(1) Integer id) {
        clienteService.borrar(id);
    }

    @GetMapping("/clientes")
    public ResponseEntity<List<ListClienteViewModel>> listar() {
        List<ClienteDTOResponse> resp = clienteService.listar();
        List<ListClienteViewModel> ltvm = clientePresenter.presentList(resp);
        return ResponseEntity.ok(ltvm);
    }

    @GetMapping("/clientes/{id}")
    public ResponseEntity<ClienteViewModel> buscar(@PathVariable("id") @Min(1) Integer id) {
        ClienteDTOResponse resp = clienteService.buscar(id);
        ClienteViewModel cvm = clientePresenter.present(resp);
        return ResponseEntity.ok(cvm);
    }

    @GetMapping("/clientes/buscar")
    public ResponseEntity<List<ListClienteViewModel>> buscarPorFiltros(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String apellido,
            @RequestParam(required = false) String nitCi) {
        List<ClienteDTOResponse> resp = clienteService.buscarPorFiltros(nombre, apellido, nitCi);
        List<ListClienteViewModel> ltvm = clientePresenter.presentList(resp);
        return ResponseEntity.ok(ltvm);
    }
}
