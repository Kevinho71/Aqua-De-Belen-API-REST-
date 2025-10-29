package com.perfumeria.aquadebelen.aquadebelen.inventario.service;

import org.springframework.stereotype.Service;

import com.perfumeria.aquadebelen.aquadebelen.compras.model.Compra;
import com.perfumeria.aquadebelen.aquadebelen.inventario.model.Lote;

@Service
public class LoteService {
    

    public void createLote(Compra compra) {
        Lote lote = new Lote();
        lote.setId(null);
    }

}
