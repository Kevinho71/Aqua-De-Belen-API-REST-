package com.perfumeria.aquadebelen.aquadebelen.reserva.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "estado_reserva")
public class EstadoReserva {


    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name="nombre")
    private String nombre;

    @OneToMany(mappedBy = "estadoReserva")
    private List<Reserva> reservas;
}
