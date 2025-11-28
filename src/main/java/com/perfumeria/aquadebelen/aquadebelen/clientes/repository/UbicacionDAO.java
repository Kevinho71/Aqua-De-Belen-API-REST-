package com.perfumeria.aquadebelen.aquadebelen.clientes.repository;

import java.util.List;

import com.perfumeria.aquadebelen.aquadebelen.clientes.model.Ubicacion;

public interface UbicacionDAO {
    public Ubicacion findById(Integer id);
    public List<Ubicacion> list();
}
