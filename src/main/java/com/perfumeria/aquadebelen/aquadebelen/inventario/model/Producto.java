package com.perfumeria.aquadebelen.aquadebelen.inventario.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.query.sqm.FetchClauseType;

import com.perfumeria.aquadebelen.aquadebelen.compras.model.DetalleCompra;
import com.perfumeria.aquadebelen.aquadebelen.compras.model.PrecioHistorico;
import com.perfumeria.aquadebelen.aquadebelen.reserva.model.DetalleReserva;
import com.perfumeria.aquadebelen.aquadebelen.ventas.model.DetalleVenta;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
    @Column(name = "id")
    private Integer id;


    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "nombre")
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "tipo_producto_id")
    private TipoProducto tipoProducto;

    @OneToMany(mappedBy = "producto", fetch = FetchType.LAZY)
    private List<Sublote> sublotes;

    @OneToMany(mappedBy = "producto", fetch = FetchType.LAZY)
    private List<DetalleVenta> detallesVentas;

    @OneToMany(mappedBy = "producto", fetch = FetchType.LAZY)
    private List<DetalleReserva> detallesReserva;

    @OneToMany(mappedBy = "producto", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<PrecioHistorico> preciosHistoricos;

    @OneToMany(mappedBy = "producto", fetch = FetchType.LAZY)
    private List<DetalleCompra> detallesCompras;

    public Producto(String descripcion, String nombre, TipoProducto tipoProducto) {
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.tipoProducto = tipoProducto;
    }

    public void addPrecioHistorico(PrecioHistorico precioHistorico){

        if(this.preciosHistoricos==null){
            preciosHistoricos = new ArrayList<>();
        }

        preciosHistoricos.add(precioHistorico);
    }

}
