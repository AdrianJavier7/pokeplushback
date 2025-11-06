package com.example.pokeplushback.Dto;

import com.example.pokeplushback.Entidades.Direccion;
import com.example.pokeplushback.Enums.Nivel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
public class UsuarioDTO {
    private Integer id;
    private String nombre;
    private String apellidos;
    private String descripcion;
    private String email;
    private Date fechaNacimiento;
    private Date fechaRegistro;
    private String telefono;
    private String foto;
    private String password;
    private Nivel nivel;
    private String codigoVerificacion;
    private Direccion direccion;
}
