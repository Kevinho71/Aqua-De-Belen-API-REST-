package com.perfumeria.aquadebelen.aquadebelen.compras.model;

import org.hibernate.annotations.ManyToAny;

import com.perfumeria.aquadebelen.aquadebelen.inventario.model.Producto;
import com.perfumeria.aquadebelen.aquadebelen.inventario.model.Sublote;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "detalle_compra")
public class DetalleCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "cantidad")
    private double cantidad;

    @Column(name = "costo_unitario")
    private double costoUnitario;

    @Column(name = "descuento")
    private double descuento;

    @Column(name="subtotal")
    private double subtotal;

    @ManyToOne
    @JoinColumn(name = "compra_id")
    private Compra compra;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "sublote_id")
    private Sublote sublote;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    @OneToOne(mappedBy = "detalleCompraId", cascade = CascadeType.PERSIST)
    private Movimiento movimiento;

    public DetalleCompra(double costoUnitario, double descuento, Producto producto) {
        this.costoUnitario = costoUnitario;
        this.descuento = descuento;
        this.producto = producto;
    }

    
}
