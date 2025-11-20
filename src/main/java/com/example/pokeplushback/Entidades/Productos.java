package com.example.pokeplushback.Entidades;

import com.example.pokeplushback.Enums.Tipos;
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

    @Enumerated(EnumType.STRING)
    @Column (name = "tipo")
    private Tipos tipo;

    @Enumerated(EnumType.STRING)
    @Column (name = "tipo2")
    private Tipos tipo2;

    @Column (name= "descripcion")
    private String descripcion;

    @Column (name= "habilitado")
    private Boolean habilitado = true;

    @Lob
    @Column(name = "foto")
    private Long foto;

    @Column (name= "stock")
    private Integer stock;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
    private List<Opiniones> opiniones;
}
