package com.example.pokeplushback.Controladores;

import com.example.pokeplushback.Dto.OpinionesDTO;
import com.example.pokeplushback.Entidades.Opiniones;
import com.example.pokeplushback.Servicios.OpinionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/opiniones")
public class OpinionesController {

    @Autowired
    private OpinionesService opinionesService;

    // ---------------- CRUD ----------------

    // Obtener todas las opiniones (devuelve entidades; si prefieres DTOs, adapta el service)
    @GetMapping("/all")
    public List<Opiniones> obtenerTodasLasOpiniones() {
        return opinionesService.obtenerTodasLasOpiniones();
    }

    // Obtener una opinion por su id -> devuelve DTO
    @GetMapping("/{id}")
    public ResponseEntity<OpinionesDTO> getOpinionPorId(@PathVariable Integer id) {
        OpinionesDTO opinion = opinionesService.obtenerOpinionPorId(id);
        if (opinion != null) {
            return ResponseEntity.ok(opinion);
        }
        return ResponseEntity.notFound().build();
    }

    // Crear una nueva opinion -> recibe DTO, devuelve DTO (201 Created)
    @PostMapping("/")
    public ResponseEntity<OpinionesDTO> crearOpinion(@Valid @RequestBody OpinionesDTO opinionDto) {
        OpinionesDTO creado = opinionesService.crearOpinion(opinionDto);
        return ResponseEntity.status(201).body(creado);
    }

    // Actualizar una opinion existente -> recibe DTO y devuelve DTO
    @PutMapping("/{id}")
    public ResponseEntity<OpinionesDTO> actualizarOpinion(@PathVariable Integer id, @Valid @RequestBody OpinionesDTO opinionDto) {
        OpinionesDTO actualizado = opinionesService.actualizarOpinion(id, opinionDto);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        }
        return ResponseEntity.notFound().build();
    }


    // Eliminar una opinion por su id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarOpinion(@PathVariable Integer id) {
        boolean eliminado = opinionesService.eliminarOpinion(id);
        if (eliminado) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.notFound().build(); // 404 Not Found
    }

    // Listar opiniones de un producto -> devuelve DTOs
    @GetMapping("/producto/{idProducto}")
    public ResponseEntity<List<OpinionesDTO>> obtenerOpinionesPorProducto(@PathVariable Integer idProducto) {
        List<OpinionesDTO> list = opinionesService.listarOpinionesPorProducto(idProducto);
        return ResponseEntity.ok(list);
    }

}
