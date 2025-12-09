package com.example.pokeplushback.conversores;

import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface CategoriaMapper {

    /*
    //De entidad a DTO
    @Mapping(source = "fecha_creacion", target = "fechaCreacion", dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    CategoriaDTO convertirADTO(Categoria entity);
    List<CategoriaDTO> convertirADTO(List<Categoria> entity);

    //De DTO a entidad
    @Mapping(source = "fechaCreacion", target = "fecha_creacion", dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    Categoria convertirAEntity(CategoriaDTO dto);
    List<Categoria> convertirAEntity(List<CategoriaDTO> dto);
     */
}