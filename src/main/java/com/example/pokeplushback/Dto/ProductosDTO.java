package com.example.pokeplushback.Dto;

import com.example.pokeplushback.Enums.Tipos;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductosDTO {

    private Integer id;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private Tipos tipo;
    private Tipos tipo2;
    private String foto;
    private int stock;
    private boolean habilitado;

}
