package com.example.pokeplushback.Dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
// Este DTO se utiliza para las respuesta de las APIs y ver los token
public class RespuestaDTO {
    private Integer estado;
    private String token;
    private String mensaje;
    private Object cuerpo;
    private String rol;
}
