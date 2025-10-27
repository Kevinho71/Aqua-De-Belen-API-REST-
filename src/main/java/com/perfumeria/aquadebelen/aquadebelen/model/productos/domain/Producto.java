package com.perfumeria.aquadebelen.aquadebelen.model.productos.domain;

import java.util.List;

import com.perfumeria.aquadebelen.aquadebelen.model.lotes.domain.Sublote;
import com.perfumeria.aquadebelen.aquadebelen.model.reserva.domain.DetalleReserva;
import com.perfumeria.aquadebelen.aquadebelen.model.transaccion.domain.DetalleTransaccion;

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
@Table(name = "producto")
public class Producto {

    @Id
    @Column(name = "id")
    private Integer id; // ID manual (sin @GeneratedValue)

    @Column(name = "precio")
    private Double precio;

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
    private List<DetalleTransaccion> detallesTransaccion;

    @OneToMany(mappedBy = "producto")
    private List<DetalleReserva> detallesReserva;

    public Producto(Double precio, String descripcion, String nombre, TipoProducto tipoProducto) {
        this.precio = precio;
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.tipoProducto = tipoProducto;
    }
}


/*package com.perfumeria.aquadebelen.aquadebelen.model.productos.domain;

import java.util.List;

import com.perfumeria.aquadebelen.aquadebelen.model.lotes.domain.Sublote;
import com.perfumeria.aquadebelen.aquadebelen.model.reserva.domain.DetalleReserva;
import com.perfumeria.aquadebelen.aquadebelen.model.transaccion.domain.DetalleTransaccion;

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
    private Double precio;

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
    private List<DetalleTransaccion> detallesTransaccion;

    @OneToMany(mappedBy = "producto")
    private List<DetalleReserva> detallesReserva;

    public Producto(Double precio, String descripcion, String nombre, TipoProducto tipoProducto) {
        this.precio = precio;
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.tipoProducto = tipoProducto;
    }

    

}*/
