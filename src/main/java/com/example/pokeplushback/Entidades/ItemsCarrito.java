package com.example.pokeplushback.Entidades;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;


@Entity
@Table(name = "carrito_items", schema = "pokeplush", catalog = "postgres")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemsCarrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Column (name = "precio_unitario", scale = 2 ,nullable = false)
    private BigDecimal precioUnitario;

    @ManyToOne
    @JoinColumn(name = "id_carrito")
    private Carrito carrito;

    // Muchos a uno con Producto
    @ManyToOne
    @JoinColumn(name = "id_producto")
    private Productos producto;
}
