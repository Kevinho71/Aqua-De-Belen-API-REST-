package com.perfumeria.aquadebelen.aquadebelen.clientes.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.perfumeria.aquadebelen.aquadebelen.clientes.model.Ubicacion;
import com.perfumeria.aquadebelen.aquadebelen.clientes.repository.UbicacionDAO;

@Service
public class UbicacionService {

    private final UbicacionDAO ubicacionDAO;

    public UbicacionService(UbicacionDAO ubicacionDAO) {
        this.ubicacionDAO = ubicacionDAO;
    }

    public List<Ubicacion> listar() {
        return ubicacionDAO.list();
    }
}
