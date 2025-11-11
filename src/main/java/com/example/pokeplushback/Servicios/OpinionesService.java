package com.example.pokeplushback.Servicios;

import com.example.pokeplushback.Dto.OpinionesDTO;
import com.example.pokeplushback.Entidades.Opiniones;
import com.example.pokeplushback.Entidades.Productos;
import com.example.pokeplushback.Entidades.Usuario;
import com.example.pokeplushback.Repositorios.OpinionesRepository;
import com.example.pokeplushback.Repositorios.ProductosRepository;
import com.example.pokeplushback.Repositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OpinionesService {

    @Autowired
    private OpinionesRepository opinionesRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProductosRepository productosRepository;

    // ===================== CREAR =====================
    public OpinionesDTO crearOpinion(OpinionesDTO dto, Usuario usuario) {
        Productos producto = productosRepository.findById(dto.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Opiniones opinion = new Opiniones();
        opinion.setUsuario(usuario);
        opinion.setProducto(producto);
        opinion.setComentario(dto.getComentario());
        opinion.setOpinion(dto.getOpinion());
        opinion.setUsuario(usuario);

        Opiniones guardada = opinionesRepository.save(opinion);
        dto.setId(guardada.getId());
        return dto;
    }

    // ===================== LEER =====================
    public List<OpinionesDTO> obtenerTodosComentarios() {
        return opinionesRepository.findAll().stream().map(o -> {
            OpinionesDTO dto = new OpinionesDTO();
            dto.setId(o.getId());
            dto.setUsuarioId(o.getUsuario().getId());
            dto.setComentario(o.getComentario());
            return dto;
        }).collect(Collectors.toList());
    }

    // ===================== LEER TODAS LAS OPINIONES =====================
    public List<OpinionesDTO> obtenerTodasOpiniones() {
        return opinionesRepository.findAll().stream().map(o -> {
            OpinionesDTO dto = new OpinionesDTO();
            dto.setId(o.getId());
            dto.setUsuarioId(o.getUsuario().getId());
            dto.setProductoId(o.getProducto().getId());
            dto.setComentario(o.getComentario());
            dto.setOpinion(o.getOpinion());
            return dto;
        }).collect(Collectors.toList());
    }

    // ===================== LEER POR ID LA OPINION =====================
    public OpinionesDTO obtenerOpinionPorId(Integer id) {
        Opiniones o = opinionesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Opinión no encontrada"));
        OpinionesDTO dto = new OpinionesDTO();
        dto.setId(o.getId());
        dto.setUsuarioId(o.getUsuario().getId());
        dto.setProductoId(o.getProducto().getId());
        dto.setComentario(o.getComentario());
        dto.setOpinion(o.getOpinion());
        return dto;
    }

    // ===================== LEER POR USUARIO ID =====================
    public List<OpinionesDTO> obtenerOpinionesPorUsuarioId(Integer usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return opinionesRepository.findAll().stream()
                .filter(o -> o.getUsuario().getId().equals(usuarioId))
                .map(o -> {
                    OpinionesDTO dto = new OpinionesDTO();
                    dto.setId(o.getId());
                    dto.setUsuarioId(o.getUsuario().getId());
                    dto.setProductoId(o.getProducto().getId());
                    dto.setComentario(o.getComentario());
                    dto.setOpinion(o.getOpinion());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    // ===================== ACTUALIZAR UNA OPINION =====================
    public OpinionesDTO actualizarOpinion(Integer id, OpinionesDTO dto) {
        Opiniones o = opinionesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Opinión no encontrada"));

        // Actualizar campos si vienen en el DTO
        if (dto.getComentario() != null) o.setComentario(dto.getComentario());
        if (dto.getOpinion() != null) o.setOpinion(dto.getOpinion());

        if (dto.getUsuarioId() != null) {
            Usuario u = usuarioRepository.findById(dto.getUsuarioId())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            o.setUsuario(u);
        }

        if (dto.getProductoId() != null) {
            Productos p = productosRepository.findById(dto.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
            o.setProducto(p);
        }

        Opiniones actualizado = opinionesRepository.save(o);
        return obtenerOpinionPorId(actualizado.getId());
    }

    // ===================== ELIMINAR =====================
    public void eliminarOpinion(Integer id) {
        if (!opinionesRepository.existsById(id)) {
            throw new RuntimeException("Opinión no encontrada");
        }
        opinionesRepository.deleteById(id);
    }

    // ===================== LEER POR PRODUCTO ID =====================

    public List<OpinionesDTO> obtenerOpinionesPorProductoId(Integer productoId) {
        Productos producto = productosRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        return opinionesRepository.findAll().stream()
                .filter(o -> o.getProducto().getId().equals(productoId))
                .map(o -> {
                    OpinionesDTO dto = new OpinionesDTO();
                    dto.setId(o.getId());
                    dto.setUsuarioId(o.getUsuario().getId());
                    dto.setProductoId(o.getProducto().getId());
                    dto.setComentario(o.getComentario());
                    dto.setOpinion(o.getOpinion());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
