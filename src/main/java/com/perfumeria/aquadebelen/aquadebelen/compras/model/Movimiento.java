package com.perfumeria.aquadebelen.aquadebelen.compras.model;

import java.time.LocalDateTime;
import java.util.Optional;

import com.perfumeria.aquadebelen.aquadebelen.ventas.model.DetalleVenta;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "movimiento")
public class Movimiento {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "cantidad")
    private double cantidad;

    @Column(name = "costo_unitario")
    private double costoUnitario;

    @Column(name = "precio_unitario")
    private double precioUnitario;

    @Column(name = "costo_total")
    private double costoTotal;

    @Column(name = "precio_total")
    private double precioTotal;

    @Column(name = "fecha")
    private LocalDateTime fecha;
    
    @Column(name = "referencia_tipo")
    private String referenciaTipo;

    @OneToOne
    @JoinColumn(name = "detalle_compra_id")
    private DetalleCompra detalleCompraId;

    @OneToOne
    @JoinColumn(name = "detalle_venta_id")
    private DetalleVenta detalleVentaId;

    @Column(name = "referencia_id")
    private Integer referenciaId;
}
