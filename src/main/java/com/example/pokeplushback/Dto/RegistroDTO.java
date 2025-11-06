package com.example.pokeplushback.Dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class RegistroDTO {
    private Integer id;
    private String email;
    private String password;
}
