package com.example.pokeplushback.Servicios;

import com.example.pokeplushback.Dto.OpinionesDTO;
import com.example.pokeplushback.Entidades.Opiniones;
import com.example.pokeplushback.Entidades.Productos;
import com.example.pokeplushback.Entidades.Usuario;
import com.example.pokeplushback.Repositorios.OpinionesRepository;
import com.example.pokeplushback.Repositorios.ProductosRepository;
import com.example.pokeplushback.Repositorios.UsuarioRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
@Getter
@Setter
public class OpinionesService {

    private final OpinionesRepository opinionesRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductosRepository productosRepository;

    // Obtener todas pero devolviendo entidades (si quieres DTOs, adapta)
    public List<Opiniones> obtenerTodasLasOpiniones() {
        return opinionesRepository.findAll();
    }

    // Obtener por id -> devuelve DTO correctamente mapeado
    public OpinionesDTO obtenerOpinionPorId(Integer id) {
        return opinionesRepository.findById(id)
                .map(this::toDTO)
                .orElse(null);
    }

    // Crear opinión -> recibe DTO y devuelve DTO
    public OpinionesDTO crearOpinion(OpinionesDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + dto.getUsuarioId()));

        Productos producto = productosRepository.findById(dto.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + dto.getProductoId()));

        Opiniones ent = new Opiniones();
        ent.setComentario(dto.getComentario());
        ent.setOpinion(dto.getOpinion());
        ent.setUsuario(usuario);
        ent.setProducto(producto); // asegúrate que en tu entidad la propiedad se llame 'producto'

        Opiniones saved = opinionesRepository.save(ent);
        return toDTO(saved);
    }

    // Actualizar -> recibe DTO (con campos a actualizar) y devuelve DTO
    public OpinionesDTO actualizarOpinion(Integer id, Opiniones dto) {
        return opinionesRepository.findById(id).map(opinion -> {
            if (dto.getComentario() != null) opinion.setComentario(dto.getComentario());
            if (dto.getOpinion() != null) opinion.setOpinion(dto.getOpinion());

            if (dto.getUsuarioId() != null) {
                Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                        .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + dto.getUsuarioId()));
                opinion.setUsuario(usuario);
            }

            if (dto.getProductoId() != null) {
                Productos producto = productosRepository.findById(dto.getProductoId())
                        .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + dto.getProductoId()));
                opinion.setProducto(producto);
            }

            Opiniones saved = opinionesRepository.save(opinion);
            return toDTO(saved);
        }).orElse(null);
    }

    // Eliminar
    public boolean eliminarOpinion(Integer id) {
        if (opinionesRepository.existsById(id)) {
            opinionesRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Obtener opiniones por producto (usa repo optimizado)
    public List<OpinionesDTO> listarOpinionesPorProducto(Integer idProducto) {
        return opinionesRepository.findByProductoIdOrderByIdDesc(idProducto)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Mapeadores
    private OpinionesDTO toDTO(Opiniones op) {
        OpinionesDTO dto = new OpinionesDTO();
        dto.setId(op.getId());
        dto.setComentario(op.getComentario());
        dto.setOpinion(op.getOpinion());
        if (op.getUsuario() != null) {
            dto.setUsuarioId(op.getUsuario().getId());
            // dto.setNombreUsuario(op.getUsuario().getNombre()); // si tienes campo nombre en Usuario
        }
        if (op.getProducto() != null) {
            dto.setProductoId(op.getProducto().getId());
            // dto.setNombreProducto(op.getProducto().getNombre()); // si tienes
        }
        return dto;
    }
}
