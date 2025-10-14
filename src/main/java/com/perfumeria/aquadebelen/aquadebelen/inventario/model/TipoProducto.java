package com.perfumeria.aquadebelen.aquadebelen.inventario.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tipo_producto")
public class TipoProducto {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name="nombre")
    private String nombre;

    @OneToMany(mappedBy = "tipoProducto")
    private List<Producto> productos;

    public TipoProducto(String nombre){
        this.nombre=nombre;
    }

}
