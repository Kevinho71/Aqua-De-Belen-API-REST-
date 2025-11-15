package com.perfumeria.aquadebelen.aquadebelen.clientes.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "nivel_fidelidad")
public class NivelFidelidad {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "nivel")
    private String nivel;

    @OneToMany(mappedBy="nivelFidelidad")
    private List<Cliente> clientes;

    public NivelFidelidad(String nivel) {
        this.nivel = nivel;
    }

    
}
