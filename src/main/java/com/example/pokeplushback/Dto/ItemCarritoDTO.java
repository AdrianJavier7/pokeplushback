package com.example.pokeplushback.Dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemCarritoDTO {
    private Integer id;
    private BigDecimal precioUnitario;
    private Integer cantidad;
    private Integer idCarrito;
    private Integer idProducto;
}
