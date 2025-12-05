package com.example.pokeplushback.Dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.annotations.BatchSize;

import java.math.BigDecimal;


@Data
public class OpinionesDTO {

    private Integer id;

    @NotNull
    private Integer productoId;

    @DecimalMin("0")
    @DecimalMax("5")
    private BigDecimal opinion;

    @Size(min=5, max=500)
    private String comentario;
    private String nombreUsuario;
    private Integer idUsuario;

}
