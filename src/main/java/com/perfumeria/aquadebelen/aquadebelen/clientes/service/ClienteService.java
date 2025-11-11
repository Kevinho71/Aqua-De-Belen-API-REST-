package com.perfumeria.aquadebelen.aquadebelen.clientes.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.perfumeria.aquadebelen.aquadebelen.clientes.DTO.ClienteDTORequest;
import com.perfumeria.aquadebelen.aquadebelen.clientes.DTO.ClienteDTOResponse;
import com.perfumeria.aquadebelen.aquadebelen.clientes.model.Cliente;
import com.perfumeria.aquadebelen.aquadebelen.clientes.model.NivelFidelidad;
import com.perfumeria.aquadebelen.aquadebelen.clientes.model.Ubicacion;
import com.perfumeria.aquadebelen.aquadebelen.clientes.repository.ClienteDAO;
import com.perfumeria.aquadebelen.aquadebelen.clientes.repository.NivelFidelidadDAO;
import com.perfumeria.aquadebelen.aquadebelen.clientes.repository.UbicacionDAO;

@Service
public class ClienteService {

    private final ClienteDAO clienteDAO;
    private final NivelFidelidadDAO nivelFidelidadDAO;
    private final UbicacionDAO ubicacionDAO;

    public ClienteService(ClienteDAO clienteDAO, NivelFidelidadDAO nivelFidelidadDAO, 
                         UbicacionDAO ubicacionDAO) {
        this.clienteDAO = clienteDAO;
        this.nivelFidelidadDAO = nivelFidelidadDAO;
        this.ubicacionDAO = ubicacionDAO;
    }

    public ClienteDTOResponse store(Integer id, ClienteDTORequest req) {
        Cliente cliente = new Cliente();
        
        if (id == null) {
            cliente.setId(clienteDAO.nextId());
        } else {
            cliente = clienteDAO.findById(id);
        }
        
        cliente.setNombre(req.nombre());
        cliente.setApellido(req.apellido());
        cliente.setTelefono(req.telefono());
        cliente.setNitCi(req.nitCi());
        cliente.setDireccion(req.direccion());
        
        NivelFidelidad nivelFidelidad = nivelFidelidadDAO.findById(req.nivelFidelidadId());
        cliente.setNivelFidelidad(nivelFidelidad);
        
        Ubicacion ubicacion = ubicacionDAO.findById(req.ubicacionId());
        cliente.setUbicacion(ubicacion);
        
        clienteDAO.store(cliente);
        
        return mapToDTOResponse(cliente);
    }

    public void borrar(Integer id) {
        clienteDAO.deleteById(id);
    }

    public ClienteDTOResponse buscar(Integer id) {
        Cliente cliente = clienteDAO.findById(id);
        return mapToDTOResponse(cliente);
    }

    public List<ClienteDTOResponse> buscarPorFiltros(String nombre, String apellido, String nitCi) {
        List<Cliente> clientes = clienteDAO.findByFiltros(nombre, apellido, nitCi);
        List<ClienteDTOResponse> response = new ArrayList<>();
        
        for (Cliente cliente : clientes) {
            response.add(mapToDTOResponse(cliente));
        }
        
        return response;
    }

    public List<ClienteDTOResponse> listar() {
        List<Cliente> clientes = clienteDAO.findAll();
        List<ClienteDTOResponse> response = new ArrayList<>();
        
        for (Cliente cliente : clientes) {
            response.add(mapToDTOResponse(cliente));
        }
        
        return response;
    }

    private ClienteDTOResponse mapToDTOResponse(Cliente cliente) {
        return new ClienteDTOResponse(
            cliente.getId(),
            cliente.getNombre(),
            cliente.getApellido(),
            cliente.getTelefono(),
            cliente.getNitCi(),
            cliente.getDireccion(),
            cliente.getNivelFidelidad().getNivel(),
            cliente.getUbicacion().getCiudad() + " - " + cliente.getUbicacion().getZona()
        );
    }
}
