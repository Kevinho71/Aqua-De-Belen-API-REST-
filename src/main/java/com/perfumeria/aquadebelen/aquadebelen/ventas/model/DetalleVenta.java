    package com.perfumeria.aquadebelen.aquadebelen.ventas.model;

    import com.perfumeria.aquadebelen.aquadebelen.inventario.model.Producto;
import com.perfumeria.aquadebelen.aquadebelen.inventario.model.Sublote;

import jakarta.persistence.Column;
    import jakarta.persistence.Entity;
    import jakarta.persistence.GeneratedValue;
    import jakarta.persistence.GenerationType;
    import jakarta.persistence.Id;
    import jakarta.persistence.JoinColumn;
    import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Entity
    @Table(name = "detalle_venta")
    public class DetalleVenta {

        @Id
        @GeneratedValue(strategy=GenerationType.IDENTITY)
        @Column(name = "id")
        private Integer id;

        @Column(name = "cantidad")
        private double cantidad;

        @Column(name = "subtotal")
        private double subtotal;

        @Column(name = "descuento") 
        private double descuento;

        @ManyToOne
        @JoinColumn(name = "producto_id")
        private Producto producto;

        @ManyToOne
        @JoinColumn(name = "venta_id")
        private Venta venta;


        @Override
        public String toString() {
            return "DetalleTransaccion [id=" + id + ", cantidad=" + cantidad + ", subtotal=" + subtotal + ", producto="
                    + producto + ", venta=" + venta.getId() + "]";
        }

        
    }
