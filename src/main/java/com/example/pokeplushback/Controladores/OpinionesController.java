package com.example.pokeplushback.Controladores;

import com.example.pokeplushback.Dto.OpinionesDTO;
import com.example.pokeplushback.Entidades.Opiniones;
import com.example.pokeplushback.Entidades.Usuario;
import com.example.pokeplushback.Security.JWTService;
import com.example.pokeplushback.Servicios.OpinionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/opiniones")
public class OpinionesController {

    @Autowired
    private OpinionesService opinionesService;

    @Autowired
    private JWTService jwtService;

    //        ------------- los metodos CRUD --------------

    //Obtener todas las opiniones
    @GetMapping("/all")
    public List<OpinionesDTO> obtenerTodasLasOpiniones() {
        return opinionesService.obtenerTodasLasOpiniones();
    }

    //Obtener una opinion por su id
    @GetMapping("/{id}")
    public ResponseEntity<OpinionesDTO> getOpinionPorId(@PathVariable Integer id) {
        OpinionesDTO opinion = opinionesService.obtenerOpinionPorId(id);
        if (opinion != null) {
            return ResponseEntity.ok(opinion);
        }
        return ResponseEntity.notFound().build();
    }

    //Crear una nueva opinion
    @PostMapping("/crear-opinion")
    public OpinionesDTO crearOpinion(@RequestBody OpinionesDTO opinion, @RequestHeader("Authorization") String token) {

        Usuario usuario = jwtService.extraerPerfilToken(token);

        return opinionesService.crearOpinion(opinion, usuario);
    }

    //Actualizar una opinion existente
    @PutMapping("/{id}")
    public ResponseEntity<OpinionesDTO> actualizarOpinion(@PathVariable Integer id, @RequestBody OpinionesDTO opinionActualizada, @RequestHeader("Authorization") String token) {

        Usuario usuario = jwtService.extraerPerfilToken(token);

        OpinionesDTO opinion = opinionesService.actualizarOpinion(id, opinionActualizada, usuario);
        if (opinion != null) {
            return ResponseEntity.ok(opinion); //esto es un 200 OK que es lo que devuelve por defecto el metodo ok()
        }
        return ResponseEntity.notFound().build(); // 404 Not Found
    }

    //Eliminar una opinion por su id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarOpinion(@PathVariable Integer id) {
        boolean eliminado = opinionesService.eliminarOpinion(id);
        if (eliminado) {
            return ResponseEntity.noContent().build(); //204 No Content
        }
        return ResponseEntity.notFound().build(); //404 Not Found
    }


    //        ------------- los metodos adicionales --------------

    @GetMapping("/producto/{idProducto}")
    public List<OpinionesDTO> obtenerOpinionesPorProducto(@PathVariable Integer idProducto) {
        return opinionesService.obtenerOpinionesPorProducto(idProducto);
    }
}
