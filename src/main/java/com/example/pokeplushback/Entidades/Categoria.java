/*
package com.example.pokeplushback.Entidades;

import com.example.pokeplushback.Enums.CategoriaEnum;
import com.example.pokeplushback.Enums.Tipos;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "categoria", schema = "pokeplush" , catalog = "postgres")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Categoria implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre")
    private String nombre;

    @Size(max = 500, message = "La descripción no puede tener más de 500 caracteres")
    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "imagen")
    private String imagen;

    @Column(name = "activo")
    private Boolean activo;

    @PastOrPresent(message = "La fecha de creación no puede ser futura")
    @Column(name = "fecha_creacion")
    private Date fecha_creacion;

    @NotNull(message = "El tipo de categoría es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column (name = "categoriaEnum")
    private CategoriaEnum categoriaEnum;

    /* Relación con Usuario (Muchos a Uno) (Lado de N)
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
    */

    /* Relación con Usuario (Uno a Uno)
    @OneToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
    */

    /* Relación con Usuario (Muhcos a Muchos)
    @ManyToMany
    @JoinTable(
            name = "categoria_usuario",
            joinColumns = @JoinColumn(name = "id_categoria"),
            inverseJoinColumns = @JoinColumn(name = "id_usuario")
    )
    private List<Usuario> usuarios;
     */
