package com.perfumeria.aquadebelen.aquadebelen.inventario.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "pedido_sugerido")
public class PedidoSugerido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @Column(name = "cantidad_sugerida", nullable = false)
    private Integer cantidadSugerida;

    @Column(name = "fecha_sugerida", nullable = false)
    private LocalDate fechaSugerida;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 20)
    private EstadoPedidoSugerido estado;

    @Column(name = "observacion", columnDefinition = "TEXT")
    private String observacion;

    @Column(name = "stock_actual_momento")
    private Double stockActualMomento;

    @Column(name = "rop_momento")
    private Integer ropMomento;

    public enum EstadoPedidoSugerido {
        PENDIENTE,
        APROBADO,
        RECHAZADO,
        EJECUTADO
    }

    // Constructors
    public PedidoSugerido() {
    }

    public PedidoSugerido(Producto producto, Integer cantidadSugerida, LocalDate fechaSugerida, 
                         EstadoPedidoSugerido estado, Double stockActualMomento, Integer ropMomento) {
        this.producto = producto;
        this.cantidadSugerida = cantidadSugerida;
        this.fechaSugerida = fechaSugerida;
        this.estado = estado;
        this.stockActualMomento = stockActualMomento;
        this.ropMomento = ropMomento;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Integer getCantidadSugerida() {
        return cantidadSugerida;
    }

    public void setCantidadSugerida(Integer cantidadSugerida) {
        this.cantidadSugerida = cantidadSugerida;
    }

    public LocalDate getFechaSugerida() {
        return fechaSugerida;
    }

    public void setFechaSugerida(LocalDate fechaSugerida) {
        this.fechaSugerida = fechaSugerida;
    }

    public EstadoPedidoSugerido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedidoSugerido estado) {
        this.estado = estado;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Double getStockActualMomento() {
        return stockActualMomento;
    }

    public void setStockActualMomento(Double stockActualMomento) {
        this.stockActualMomento = stockActualMomento;
    }

    public Integer getRopMomento() {
        return ropMomento;
    }

    public void setRopMomento(Integer ropMomento) {
        this.ropMomento = ropMomento;
    }
}
