/*
package com.example.pokeplushback.conversores;

import com.example.pokeplushback.Dto.CategoriaDTO;
import com.example.pokeplushback.Dto.ProductosDTO;
import com.example.pokeplushback.Entidades.Categoria;
import com.example.pokeplushback.Entidades.Productos;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "Spring")
public interface CategoriaMapper {

    //De entidad a DTO
    @Mapping(source = "fecha_creacion", target = "fechaCreacion", dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    CategoriaDTO convertirADTO(Categoria entity);
    List<CategoriaDTO> convertirADTO(List<Categoria> entity);

    //De DTO a entidad
    @Mapping(source = "fechaCreacion", target = "fecha_creacion", dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    Categoria convertirAEntity(CategoriaDTO dto);
    List<Categoria> convertirAEntity(List<CategoriaDTO> dto);
}
*/