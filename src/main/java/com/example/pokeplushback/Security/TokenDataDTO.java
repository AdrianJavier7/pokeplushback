package com.example.pokeplushback.Security;

import com.example.pokeplushback.Enums.Nivel;
import lombok.Builder;
import lombok.Data;

// DTO para almacenar los datos extraídos del token JWT
@Data
@Builder
public class TokenDataDTO {
    private String email;
    private Nivel nivel;
    private Long fecha_creacion;
    private Long fecha_expiracion;
}
