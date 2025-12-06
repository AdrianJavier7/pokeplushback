package com.example.pokeplushback.Servicios;

import com.example.pokeplushback.Dto.OpinionesDTO;
import com.example.pokeplushback.Entidades.Opiniones;
import com.example.pokeplushback.Entidades.Usuario;
import com.example.pokeplushback.Repositorios.OpinionesRepository;
import com.example.pokeplushback.Repositorios.UsuarioRepository;
import com.example.pokeplushback.Repositorios.ProductosRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OpinionesService {

    @Autowired
    private OpinionesRepository opinionesRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProductosRepository productosRepository;

    /**
     * Consultar todo
     *
     * Obtener por id
     *
     * Crear
     *
     * Actualizar
     *
     * Eliminar
     *
     *
     */


    //Este lo que hace es devolver todas las opiniones que hay en la base de datos
    public List<OpinionesDTO> obtenerTodasLasOpiniones() {
        List<Opiniones> opiniones = opinionesRepository.findAll();
        return opiniones.stream().map(opinion -> {
            OpinionesDTO dto = new OpinionesDTO();
            dto.setId(opinion.getId());
            dto.setComentario(opinion.getComentario());
            dto.setOpinion(opinion.getOpinion());
            dto.setProductoId(opinion.getProducto().getId());
            return dto;
        }).toList();
    }

    //Este lo que hace es devolver una opinion en concreto buscandola por su id
    public OpinionesDTO obtenerOpinionPorId(Integer id) {
        Opiniones opinion = opinionesRepository.findById(id).orElse(null);
        if (opinion != null) {
            OpinionesDTO dto = new OpinionesDTO();
            dto.setId(opinion.getId());
            dto.setComentario(opinion.getComentario());
            dto.setOpinion(opinion.getOpinion());
            dto.setProductoId(opinion.getProducto().getId());
            return dto;
        }
        return null;
    }

    //Este lo que hace es crear una nueva opinion y guardarla en la base de datos
    public OpinionesDTO crearOpinion(OpinionesDTO opinion, Usuario usuario) {
        Opiniones nuevaOpinion = new Opiniones();
        nuevaOpinion.setComentario(opinion.getComentario());
        nuevaOpinion.setOpinion(opinion.getOpinion());
        nuevaOpinion.setUsuario(usuario);
        nuevaOpinion.setProducto(productosRepository.getById(opinion.getProductoId()));

        Opiniones opinionGuardada = opinionesRepository.save(nuevaOpinion);

        OpinionesDTO opinionDTO = new OpinionesDTO();
        opinionDTO.setId(opinionGuardada.getId());
        opinionDTO.setComentario(opinionGuardada.getComentario());
        opinionDTO.setOpinion(opinionGuardada.getOpinion());
        opinionDTO.setProductoId(opinionGuardada.getProducto().getId());
        // campos de usuario:
        opinionDTO.setIdUsuario(opinionGuardada.getUsuario().getId());
        opinionDTO.setNombreUsuario(opinionGuardada.getUsuario().getNombre());
        return opinionDTO;
    }


    //Este lo que hace es actualizar una opinion existente
    public OpinionesDTO actualizarOpinion(Integer id, OpinionesDTO opinionActualizada, Usuario usuario) {
        Opiniones opinionExistente = opinionesRepository.findById(id).orElse(null);
        if (opinionExistente != null) {
            opinionExistente.setComentario(opinionActualizada.getComentario());
            opinionExistente.setOpinion(opinionActualizada.getOpinion());
            opinionExistente.setUsuario(usuario);
            opinionExistente.setProducto(productosRepository.getById(opinionActualizada.getProductoId()));

            Opiniones opinionGuardada = opinionesRepository.save(opinionExistente);

            OpinionesDTO dto = new OpinionesDTO();
            dto.setId(opinionGuardada.getId());
            dto.setComentario(opinionGuardada.getComentario());
            dto.setOpinion(opinionGuardada.getOpinion());
            dto.setProductoId(opinionGuardada.getProducto().getId());
            dto.setIdUsuario(opinionGuardada.getUsuario().getId());
            dto.setNombreUsuario(opinionGuardada.getUsuario().getNombre());
            return dto;
        }
        return null;
    }

    //Este lo que hace es eliminar una opinion buscandola por su id
    public boolean eliminarOpinion(Integer id) {
        if (opinionesRepository.existsById(id)) {
            opinionesRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    //        ---------------- Métodos adicionales si los necesitamos  ------------------

    //Este lo que hace es devolver todas las opiniones de un producto en concreto buscandolo por su id
    public List<OpinionesDTO> obtenerOpinionesPorProducto(Integer idProducto) {
        return opinionesRepository
                .findAll()
                .stream()
                .filter(opinion -> opinion.getProducto().getId().equals(idProducto))
                .map(opinion -> {
                    OpinionesDTO dto = new OpinionesDTO();
                    dto.setId(opinion.getId());
                    dto.setComentario(opinion.getComentario());
                    dto.setOpinion(opinion.getOpinion());
                    dto.setProductoId(opinion.getProducto().getId());
                    dto.setNombreUsuario(opinion.getUsuario().getNombre());
                    dto.setIdUsuario(opinion.getUsuario().getId());
                    return dto;
                })
                .toList();
    }

    //--------------PRODUCTOS--------------------

    public List<OpinionesDTO> listarOpinionesPorProducto(Integer productoId) {
        return opinionesRepository.findByProductoId(productoId)
                .stream()
                .map(opinion -> {
                    OpinionesDTO dto = new OpinionesDTO();
                    dto.setId(opinion.getId());
                    dto.setComentario(opinion.getComentario());
                    dto.setOpinion(opinion.getOpinion());
                    dto.setProductoId(opinion.getProducto().getId());

                    // Evitar NPE: si no hay usuario, devolvemos un valor por defecto
                    if (opinion.getUsuario() != null) {
                        dto.setNombreUsuario(opinion.getUsuario().getNombre());
                        dto.setIdUsuario(opinion.getUsuario().getId());
                    } else {
                        dto.setIdUsuario(null);
                        dto.setNombreUsuario("Usuario desconocido");
                    }

                    return dto;
                })
                .toList();
    }



}