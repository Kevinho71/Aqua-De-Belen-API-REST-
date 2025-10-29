package com.perfumeria.aquadebelen.aquadebelen.inventario.model;
import java.time.*;
import java.util.ArrayList;
import java.util.List;

import com.perfumeria.aquadebelen.aquadebelen.compras.model.Compra;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="lote")
public class Lote {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name = "fecha_ingreso")
    private LocalDateTime fechaIngreso;

    //borrar esta columna
    @Column(name = "observacion")
    private String observacion;
    
    @OneToMany(mappedBy = "lote")
    private List<Sublote> sublotes;

    @OneToOne(mappedBy = "lote")
    private Compra compra;


    public void addSublote(Sublote sublote){
        if(this.sublotes==null){
            sublotes= new ArrayList<>();
        }

        sublotes.add(sublote);
        sublote.setLote(this);
    }
}
