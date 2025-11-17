package com.example.pokeplushback.Dto;

import lombok.Data;

@Data
public class CambiarContrasenaDTO {
    private String email;
    private String codigo;
    private String nuevaPassword;
}
