package com.example.pokeplushback.Entidades;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "usuario", schema = "pokeplush" , catalog = "postgres")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellidos")
    private String apellidos;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "fecha_nac")
    private Date fecha_nacimiento;

    @Column(name = "fecha_reg")
    private Date fecha_registro;

    @Column(name = "telefono")
    private String telefono;

    @Lob
    private Long foto;

    @Column(name = "password")
    private String password;

    @Column(name = "nivel")
    private String nivel;

    @Column(name = "cod_verif")
    private String codigo_verificacion;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Direccion> direcciones;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Opiniones> opiniones;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Carrito> carritos;
}
