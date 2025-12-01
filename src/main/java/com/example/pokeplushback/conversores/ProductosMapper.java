package com.example.pokeplushback.conversores;

import com.example.pokeplushback.Dto.ProductosDTO;
import com.example.pokeplushback.Entidades.Productos;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper (componentModel = "Spring")
public interface ProductosMapper {

    //De entidad a DTO
    ProductosDTO convertirADTO(Productos entity);

    List<ProductosDTO> convertirADTO(List<Productos> entity);

    //De DTO a entidad

    Productos convertirAEntity(ProductosDTO dto);

    List<Productos> convertirAEntity(List<ProductosDTO> dto);



}
