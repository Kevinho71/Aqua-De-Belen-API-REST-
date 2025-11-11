package com.perfumeria.aquadebelen.aquadebelen.clientes.model;

import java.util.List;

import org.hibernate.annotations.Cascade;

import com.perfumeria.aquadebelen.aquadebelen.reserva.model.Reserva;
import com.perfumeria.aquadebelen.aquadebelen.ventas.model.Venta;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="cliente")
public class Cliente {

    @Id
    @Column(name="id")
    private Integer id;

    @Column(name="nombre")
    private String nombre;

    @Column(name="apellido")
    private String apellido;
  
    @Column(name = "telefono")
    private String telefono;

    @Column(name = "NIT_CI")
    private String nitCi;

    @Column(name = "direccion")
    private String direccion;

    @ManyToOne()
    @JoinColumn(name = "nivel_fidelidad_id")
    private NivelFidelidad nivelFidelidad;

    @ManyToOne()
    @JoinColumn(name="ubicacion_id")
    private Ubicacion ubicacion;

    @OneToMany(mappedBy = "cliente")
    private List<Venta> ventas;

    @OneToMany(mappedBy = "cliente")
    private List<Reserva> reservas;

    public Cliente(String nombre, String apellido, String telefono, String nitCi , String direccion ,NivelFidelidad nivelFidelidad,
            Ubicacion ubicacion) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.nitCi= nitCi;
        this.direccion=direccion;
        this.nivelFidelidad = nivelFidelidad;
        this.ubicacion = ubicacion;
    }

    
}
