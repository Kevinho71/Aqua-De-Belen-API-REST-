package com.perfumeria.aquadebelen.aquadebelen.clientes.repository;

import java.util.List;

import com.perfumeria.aquadebelen.aquadebelen.clientes.model.Cliente;

public interface ClienteDAO {

    void store(Cliente cliente);
    Cliente findById(Integer id);
    List<Cliente> findAll();
    void deleteById(Integer id);
    Integer nextId();
}
