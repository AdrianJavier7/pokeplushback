/*
package com.example.pokeplushback.Servicios;

import com.example.pokeplushback.Dto.CategoriaDTO;
import com.example.pokeplushback.Entidades.Categoria;
import com.example.pokeplushback.Repositorios.CategoriaRepository;
import com.example.pokeplushback.conversores.CategoriaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private CategoriaMapper categoriaMapper;

    public CategoriaDTO crearCategoria(CategoriaDTO dto) {
        Categoria entidad = categoriaMapper.convertirAEntity(dto);
        if (entidad.getFecha_creacion() == null) {
            entidad.setFecha_creacion(new Date());
        }
        Categoria guardada = categoriaRepository.save(entidad);
        return categoriaMapper.convertirADTO(guardada);
    }

    public List<CategoriaDTO> verTodasCategorias() {
        return categoriaMapper.convertirADTO(categoriaRepository.findAll());
    }

    public CategoriaDTO verUnaCategoria(Integer id) {
        Categoria encontrada = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria no encontrada"));
        return categoriaMapper.convertirADTO(encontrada);
    }

    public CategoriaDTO editarCategoria(Integer id, CategoriaDTO dto) {
        Categoria existente = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria no encontrada"));

        Categoria temp = categoriaMapper.convertirAEntity(dto);
        if (temp.getNombre() != null) existente.setNombre(temp.getNombre());
        existente.setDescripcion(temp.getDescripcion());
        existente.setImagen(temp.getImagen());
        existente.setActivo(temp.getActivo());
        if (temp.getFecha_creacion() != null) existente.setFecha_creacion(temp.getFecha_creacion());
        existente.setCategoriaEnum(temp.getCategoriaEnum());

        Categoria actualizada = categoriaRepository.save(existente);
        return categoriaMapper.convertirADTO(actualizada);
    }

    public void eliminarCategoria(Integer id) {
        if (!categoriaRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria no encontrada");
        }
        categoriaRepository.deleteById(id);
    }
}
*/