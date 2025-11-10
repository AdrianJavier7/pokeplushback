package com.example.pokeplushback.Entidades;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
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

    @Column (name="precio", scale=2)
    private BigDecimal precio ;

    @Column (name = "tipo")
    private String tipo;

    @Column (name= "descripcion")
    private String descripcion;

    @Column (name= "habilitado")
    private Boolean habilitado = true;

    @Lob
    @Column(name = "foto", columnDefinition = "bytea")
    private Long foto;

    @Column (name= "stock")
    private Integer stock;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
    private List<Opiniones> opiniones;





}
