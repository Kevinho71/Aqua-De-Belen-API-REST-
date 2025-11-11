package com.perfumeria.aquadebelen.aquadebelen.ventas.model;

import java.util.List;

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
@Table(name = "metodo_de_pago")
public class MetodoDePago {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "metodo")
    private String metodo;

    @OneToMany(mappedBy = "metodoDePago")
    private List<Venta> ventas;

    @Override
    public String toString() {
        return "MetodoDePago [id=" + id + ", metodo=" + metodo + "]";
    }

    

}
