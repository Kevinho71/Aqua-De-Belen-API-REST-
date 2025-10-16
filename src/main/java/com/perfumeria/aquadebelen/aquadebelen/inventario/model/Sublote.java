package com.perfumeria.aquadebelen.aquadebelen.inventario.model;

import java.time.*;

import com.perfumeria.aquadebelen.aquadebelen.compras.model.DetalleCompra;
import com.perfumeria.aquadebelen.aquadebelen.ventas.model.DetalleVenta;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="sublote")
public class Sublote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVencimiento;

    @Column(name = "fecha_produccion")
    private LocalDate fechaProduccion;

    @Column(name = "codigo_sublote")
    private String codigoSublote;

    @Column(name = "cantidad_inicial")
    private double cantidadInicial;

    @Column(name = "cantidad_actual")
    private double cantidadActual;

    @Column(name = "costo_unitario")
    private double costoUnitario;

    @Column(name="estado")
    @Enumerated(EnumType.STRING)
    private EstadoSublote estado;

    @ManyToOne
    @JoinColumn(name="lote_id")
    private Lote lote;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    @OneToOne(mappedBy = "sublote")
    private DetalleCompra detalleCompra;


}
