package com.example.pokeplushback.Entidades;

import com.example.pokeplushback.Enums.Estados;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "carrito", schema = "pokeplush", catalog = "postgres")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Carrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "creado", nullable = false)
    private LocalDate creadoEn;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private Estados estado;
}
