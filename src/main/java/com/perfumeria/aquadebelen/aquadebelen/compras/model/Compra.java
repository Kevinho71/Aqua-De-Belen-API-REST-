package com.perfumeria.aquadebelen.aquadebelen.compras.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.perfumeria.aquadebelen.aquadebelen.inventario.model.Lote;
import com.perfumeria.aquadebelen.aquadebelen.inventario.model.Proveedor;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
    @Column(name = "id")
    private Integer id;

    @Column(name = "costo_bruto")
    private double costoBruto;

    @Column(name = "costo_neto")
    private double costoNeto;

    @Column(name = "descuento_total")
    private double descuentoTotal;

    @Column(name = "fecha")
    private LocalDateTime fecha;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "lote_id")   
    private Lote lote;

    @ManyToOne
    @JoinColumn(name = "proveedor_id")
    private Proveedor proveedor;

    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<DetalleCompra> detallesCompra;


    public Compra(Integer id, double costoBruto, double descuentoTotal, Proveedor proveedor,
            List<DetalleCompra> detallesCompra) {
        this.id = id;
        this.costoBruto = costoBruto;
        this.descuentoTotal = descuentoTotal;
        this.proveedor = proveedor;
        this.detallesCompra = detallesCompra;
    }

    public void addDetalle(DetalleCompra detalleCompra){
        if(this.detallesCompra==null){
            List<DetalleCompra> detalles = new ArrayList<>();
            this.setDetallesCompra(detalles);
        }

        detallesCompra.add(detalleCompra);
        detalleCompra.setCompra(this);
    }
}
