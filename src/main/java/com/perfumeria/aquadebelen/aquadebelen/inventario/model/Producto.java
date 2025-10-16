package com.perfumeria.aquadebelen.aquadebelen.inventario.model;

import java.util.List;

import com.perfumeria.aquadebelen.aquadebelen.compras.model.DetalleCompra;
import com.perfumeria.aquadebelen.aquadebelen.compras.model.PrecioHistorico;
import com.perfumeria.aquadebelen.aquadebelen.reserva.model.DetalleReserva;
import com.perfumeria.aquadebelen.aquadebelen.ventas.model.DetalleVenta;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@Table(name = "producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "precio")
    private double precio;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "nombre")
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "tipo_producto_id")
    private TipoProducto tipoProducto;

    @OneToMany(mappedBy = "producto")
    private List<Sublote> sublotes;

    @OneToMany(mappedBy = "producto")
    private List<DetalleVenta> detallesVentas;

    @OneToMany(mappedBy = "producto")
    private List<DetalleReserva> detallesReserva;

    @OneToMany(mappedBy = "producto")
    private List<PrecioHistorico> preciosHistoricos;

    @OneToMany(mappedBy = "producto")
    private List<DetalleCompra> detallesCompras;

    public Producto(double precio, String descripcion, String nombre, TipoProducto tipoProducto) {
        this.precio = precio;
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.tipoProducto = tipoProducto;
    }

    

}
