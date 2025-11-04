package com.example.pokeplushback.Entidades;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "direccion", schema = "pokeplush" , catalog = "postgres")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Direccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "direccion")
    private String direccion;
}
