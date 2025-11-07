package com.example.pokeplushback.Dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.BatchSize;


@Data
public class OpinionesDTO {

    private Integer id;

    @NotNull
    private Integer usuarioId;

    @NotNull
    private Integer productoId;

    @DecimalMin("0.0")
    @DecimalMax("5.0")
    private Float opinion;

    @Size(min=5, max=500)
    private String comentario;

}
