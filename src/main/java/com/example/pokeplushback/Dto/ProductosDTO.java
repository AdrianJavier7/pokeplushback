package com.example.pokeplushback.Dto;

import com.example.pokeplushback.Enums.Tipos;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    @Enumerated(EnumType.STRING)
    private Tipos tipo;
    private String foto;
    private int stock;
    private boolean habilitado;

}
