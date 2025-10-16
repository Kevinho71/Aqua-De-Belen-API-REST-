package com.perfumeria.aquadebelen.aquadebelen.compras.model;

import java.util.List;

import com.perfumeria.aquadebelen.aquadebelen.inventario.model.Lote;
import com.perfumeria.aquadebelen.aquadebelen.inventario.model.Proveedor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "compra")
public class Compra {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "costo_bruto")
    private double costoBruto;

    @Column(name = "costo_neto")
    private double costoNeto;

    @Column(name = "descuento")
    private double descuento;

    @OneToOne
    @JoinColumn(name = "lote_id")   
    private Lote lote;

    @ManyToOne
    @JoinColumn(name = "proveedor_id")
    private Proveedor proveedor;

    @OneToMany(mappedBy = "compra")
    private List<DetalleCompra> detallesCompra;

    public Compra(Integer id, double costoBruto, double descuento, Proveedor proveedor,
            List<DetalleCompra> detallesCompra) {
        this.id = id;
        this.costoBruto = costoBruto;
        this.descuento = descuento;
        this.proveedor = proveedor;
        this.detallesCompra = detallesCompra;
    }

    
}
