package com.perfumeria.aquadebelen.aquadebelen.reserva.model;

import java.time.*;
import java.util.List;

import com.perfumeria.aquadebelen.aquadebelen.clientes.model.Cliente;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="reserva")
public class Reserva {


    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "fecha_reserva")
    private LocalDateTime fechaReserva;

    @Column(name = "fecha_limite")
    private LocalDateTime fechaLimite;

    @Column(name = "adelanto")
    private double adelanto;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @OneToMany(mappedBy = "reserva")
    private List<DetalleReserva> detalleReservas;

    @ManyToOne
    @JoinColumn(name = "estado_reserva_id")
    private EstadoReserva estadoReserva;
}
