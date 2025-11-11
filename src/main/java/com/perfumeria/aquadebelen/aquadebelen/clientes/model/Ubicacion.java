package com.perfumeria.aquadebelen.aquadebelen.clientes.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "ubicacion")
public class Ubicacion {

    @Id
    @Column(name="id")
    private Integer id;

    @Column(name="ciudad")
    private String ciudad;

    @Column(name = "zona")
    private String zona;

    @OneToMany(mappedBy = "ubicacion")
    private List<Cliente> clientes;

    public Ubicacion(String ciudad, String zona) {
        this.ciudad = ciudad;
        this.zona = zona;
    }

    
}
