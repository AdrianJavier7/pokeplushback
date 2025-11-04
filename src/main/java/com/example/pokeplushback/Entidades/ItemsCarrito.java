package com.example.pokeplushback.Entidades;

import jakarta.persistence.*;
import lombok.*;



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

    @Column(name = "id_carrito", nullable = false)
    private Integer idCarrito;

    @Column(name = "id_producto", nullable = false)
    private Integer IdProducto;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Column (name = "precio_unitario", nullable = false)
    private Double precioUnitario;

    @ManyToOne
    @JoinColumn(name = "id_carrito")
    private Carrito carrito;

    // Muchos a uno con Producto
    @ManyToOne
    @JoinColumn(name = "id_producto")
    private Productos producto;
}
