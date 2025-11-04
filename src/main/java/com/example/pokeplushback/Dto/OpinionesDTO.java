package com.example.pokeplushback.Dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
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

    @Min(0)
    @Max(5)
    private Float opinion;

    @Size(min=5, max=500)
    private String comentario;

}
