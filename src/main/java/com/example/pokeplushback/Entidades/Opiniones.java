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

    @Column(name = "comentario")
    private String comentario;

    @Column(name = "opinion")
    private Float opinion;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_producto")
    private Productos producto;
}
