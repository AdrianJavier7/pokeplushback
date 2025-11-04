package com.example.pokeplushback.Servicios;

import com.example.pokeplushback.Entidades.Opiniones;
import com.example.pokeplushback.Repositorios.OpinionesRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OpinionesService {

    @Autowired
    private OpinionesRepository opinionesRepository;

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

    public List<Opiniones> findAll() {
        return opinionesRepository.findAll();
    }

    public Opiniones findById(Integer id) {
        return opinionesRepository.findById(id).orElse(null);
    }

    public Opiniones save(Opiniones opiniones) {
        return opinionesRepository.save(opiniones);
    }

    public void deleteById(Integer id) {
        opinionesRepository.deleteById(id);
    }

    //        ---------------- Métodos adicionales si los necesitamos  ------------------










}
