package com.example.pokeplushback.Entidades;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Table(name= "Productos", schema= "pokeplush", catalog = "postgres")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Productos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre")
    private String nombre;

    @Column (name="precio")
    private float precio;

    @Column (name = "tipo")
    private String tipo;

    @Column (name= "descripcion")
    private String descripcion;

    @Lob
    @Column (name= "foto")
    private Long foto;

    @Column (name= "stock")
    private int stock;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
    private List<Opiniones> opiniones;





}
