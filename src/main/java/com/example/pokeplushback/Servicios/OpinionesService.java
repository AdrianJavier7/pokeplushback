package com.example.pokeplushback.Servicios;

import com.example.pokeplushback.Dto.OpinionesDTO;
import com.example.pokeplushback.Entidades.Opiniones;
import com.example.pokeplushback.Repositorios.OpinionesRepository;
import com.example.pokeplushback.Repositorios.UsuarioRepository;
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
    public List<Opiniones> obtenerTodasLasOpiniones() {
        return opinionesRepository.findAll();
    }

    //Este lo que hace es devolver una opinion en concreto buscandola por su id
    public Opiniones obtenerOpinionPorId(Integer id) {
        return opinionesRepository.findById(id).orElse(null);
    }

    //Este lo que hace es crear una nueva opinion y guardarla en la base de datos
    public OpinionesDTO crearOpinion(OpinionesDTO opinion) {
        Opiniones nuevaOpinion = new Opiniones();
        nuevaOpinion.setComentario(opinion.getComentario());
        nuevaOpinion.setOpinion(opinion.getOpinion());
        nuevaOpinion.setUsuario(usuarioRepository.getById(opinion.getUsuarioId()));

        Opiniones opinionGuardada = opinionesRepository.save(nuevaOpinion);

        OpinionesDTO opinionDTO = new OpinionesDTO();
        opinionDTO.setId(opinionGuardada.getId());
        opinionDTO.setComentario(opinionGuardada.getComentario());
        opinionDTO.setOpinion(opinionGuardada.getOpinion());
        opinionDTO.setUsuarioId(opinionGuardada.getUsuario().getId());
        return opinionDTO;

    }

    //Este lo que hace es actualizar una opinion existente en la base de datos
    public Opiniones actualizarOpinion(Integer id, Opiniones opinionActualizada) {
        Opiniones opinionExistente = opinionesRepository.findById(id).orElse(null);
        if (opinionExistente != null) {
            opinionExistente.setComentario(opinionActualizada.getComentario());
            opinionExistente.setOpinion(opinionActualizada.getOpinion());
            opinionExistente.setUsuario(opinionActualizada.getUsuario());
            opinionExistente.setProducto(opinionActualizada.getProducto());
            return opinionesRepository.save(opinionExistente);
        }
        return null;
    }

    //Este lo que hace es eliminar una opinion de la base de datos buscandola por su id
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
    public List<Opiniones> obtenerOpinionesPorProducto(Integer idProducto) {
        return opinionesRepository
                .findAll()
                .stream()
                .filter(opinion -> opinion.getProducto().getId().equals(idProducto))
                .toList();
    }

    public List<Opiniones> listarOpinionesPorProducto (Integer idProducto) {
        return opinionesRepository.findByProductoIdOrderByIdDesc(idProducto);
    }
}