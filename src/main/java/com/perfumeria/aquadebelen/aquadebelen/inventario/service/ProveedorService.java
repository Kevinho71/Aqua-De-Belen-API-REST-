package com.perfumeria.aquadebelen.aquadebelen.inventario.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.perfumeria.aquadebelen.aquadebelen.clientes.repository.UbicacionDAO;
import com.perfumeria.aquadebelen.aquadebelen.inventario.DTO.ProveedorDTORequest;
import com.perfumeria.aquadebelen.aquadebelen.inventario.DTO.ProveedorDTOResponse;
import com.perfumeria.aquadebelen.aquadebelen.inventario.model.Proveedor;
import com.perfumeria.aquadebelen.aquadebelen.inventario.repository.ProveedorDAO;

@Service
public class ProveedorService {

    private ProveedorDAO pDAO;
    private UbicacionDAO uDAO;
    
    
    public ProveedorService(ProveedorDAO pDAO, UbicacionDAO uDAO) {
        this.pDAO = pDAO;
        this.uDAO = uDAO;
    }


    public ProveedorDTOResponse store(Integer id, ProveedorDTORequest req) {
        Proveedor proveedor = new Proveedor();
        if (id == null) {
            proveedor.setId(pDAO.nextId());
            proveedor.setNombre(req.nombre());
            proveedor.setCorreo(req.correo());
            proveedor.setTelefono(req.telefono());
            proveedor.setNit(req.nit());
            proveedor.setUbicacion(uDAO.findById(req.ubicacionId()));
            pDAO.store(proveedor);
        } else {
            proveedor = pDAO.findById(id);
            proveedor.setNombre(req.nombre());
            proveedor.setCorreo(req.correo());
            proveedor.setTelefono(req.telefono());
            proveedor.setNit(req.nit());
            proveedor.setUbicacion(uDAO.findById(req.ubicacionId()));
            pDAO.store(proveedor);
        }
        Proveedor proveedor2 = pDAO.findById(proveedor.getId());
        return mapToDtoResponse(proveedor2);
    }

    public ProveedorDTOResponse buscar(Integer id) {
        Proveedor proveedor = pDAO.findById(id);
        return mapToDtoResponse(proveedor);
    }

    public List<ProveedorDTOResponse> listar() {
        List<Proveedor> lista = pDAO.list();
        List<ProveedorDTOResponse> listaResp = new ArrayList<>();
        for (Proveedor p : lista) {
            ProveedorDTOResponse e = mapToDtoResponse(p);
            listaResp.add(e);
        }
        return listaResp;
    }

    public void borrar(Integer id) {
        pDAO.deleteById(id);
    }

    public ProveedorDTOResponse mapToDtoResponse(Proveedor proveedor) {
        if (proveedor == null) {
            return null;
        }

        Integer ubicacionId = null;
        String ciudad = null;
        String zona = null;
        if (proveedor.getUbicacion() != null) {
            ubicacionId = proveedor.getUbicacion().getId();
            ciudad = proveedor.getUbicacion().getCiudad();
            zona = proveedor.getUbicacion().getZona();
        }

        return new ProveedorDTOResponse(
                proveedor.getId(),
                proveedor.getNombre(),
                proveedor.getCorreo(),
                proveedor.getTelefono(),
                proveedor.getNit(),
                ubicacionId,
                ciudad,
                zona);
    }
}
