package com.example.pokeplushback.Entidades;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "opiniones", schema = "pokeplush", catalog = "postgres")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class Opiniones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    // Relación con Usuario
    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    private Usuario usuario;

    // Relación con Producto (un producto puede tener varias opiniones)
    @ManyToOne
    @JoinColumn(name = "id_producto", referencedColumnName = "id")
    private Productos productos;

    @Column(name = "comentario")
    private String comentario;

    @Column(name = "opinion")
    private Float opinion;
}
