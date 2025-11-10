package com.example.pokeplushback.Dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductosDTO {

    private Integer id;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private String tipo;
    private Long foto;
    private int stock;
    private boolean habilitado;

}
