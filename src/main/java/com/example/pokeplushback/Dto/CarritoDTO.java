package com.example.pokeplushback.Dto;

import com.example.pokeplushback.Entidades.ItemsCarrito;
import com.example.pokeplushback.Enums.Estados;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CarritoDTO {
    private Integer id;
    private LocalDate creadoEn;
    private Estados estado;
    private Integer idUsuario;
    private List<ItemCarritoDTO> items;
}
