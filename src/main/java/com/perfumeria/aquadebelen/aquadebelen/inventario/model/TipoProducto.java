package com.perfumeria.aquadebelen.aquadebelen.inventario.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
    @Column(name = "id")
    private Integer id;

    @Column(name="nombre")
    private String nombre;

    @JsonIgnore
    @OneToMany(mappedBy = "tipoProducto")
    private List<Producto> productos;

    public TipoProducto(String nombre){
        this.nombre=nombre;
    }

}
