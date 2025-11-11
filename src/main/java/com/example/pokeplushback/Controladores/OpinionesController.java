package com.example.pokeplushback.Controladores;

import com.example.pokeplushback.Dto.OpinionesDTO;
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

    // ===================== FINDALL =====================
    @GetMapping("/all")
    public ResponseEntity<List<OpinionesDTO>> findAll() {
        List<OpinionesDTO> opiniones = opinionesService.obtenerTodasOpiniones();
        return ResponseEntity.ok(opiniones);
    }

    // ===================== CREAR =====================
    @PostMapping("/crearcomentario")
    public ResponseEntity<OpinionesDTO> crearOpinion(@RequestBody OpinionesDTO dto, @RequestHeader ("Authorization") String Token) {
        Usuario perfilUsuario = jwtService.extraerPerfilToken(Token);
        OpinionesDTO creado = opinionesService.crearOpinion(dto, perfilUsuario);
        return ResponseEntity.ok(creado);
    }

    // ===================== LEER =====================
    @GetMapping("/leercomentarios")
    public ResponseEntity<List<OpinionesDTO>> obtenerTodosComentarios() {
        List<OpinionesDTO> opiniones = opinionesService.obtenerTodosComentarios();
        return ResponseEntity.ok(opiniones);
    }

    // ===================== LEER POR ID =====================
    @GetMapping("/leerporid/{id}")
    public ResponseEntity<OpinionesDTO> obtenerOpinionPorId(@PathVariable Integer id) {
        OpinionesDTO dto = opinionesService.obtenerOpinionPorId(id);
        return ResponseEntity.ok(dto);
    }

    // ===================== LEER POR PRODUCTO ID =====================
    @GetMapping("/leerporproductoid/{productoId}")
    public ResponseEntity<List<OpinionesDTO>> obtenerOpinionesPorProductoId(@PathVariable Integer productoId) {
        List<OpinionesDTO> opiniones = opinionesService.obtenerOpinionesPorProductoId(productoId);
        return ResponseEntity.ok(opiniones);
    }

    // ===================== LEER POR USUARIO ID =====================
    @GetMapping("/leerporusuarioid/{usuarioId}")
    public ResponseEntity<List<OpinionesDTO>> obtenerOpinionesPorUsuarioId(@RequestHeader ("Authorization") String Token) {
        Usuario perfilUsuario = jwtService.extraerPerfilToken(Token);
        List<OpinionesDTO> opiniones = opinionesService.obtenerOpinionesPorUsuarioId(perfilUsuario.getId());
        return ResponseEntity.ok(opiniones);
    }

    // ===================== ACTUALIZAR =====================
    @PutMapping("/actualizarcomentario/{id}")
    public ResponseEntity<OpinionesDTO> actualizarOpinion(@RequestHeader ("Authorization") String Token, @PathVariable Integer id, @RequestBody OpinionesDTO dto) {
        Usuario perfilUsuario = jwtService.extraerPerfilToken(Token);
        dto.setUsuarioId(perfilUsuario.getId());
        OpinionesDTO actualizado = opinionesService.actualizarOpinion(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    // ===================== ELIMINAR =====================
    @DeleteMapping("/eliminarcomentario/{id}")
    public ResponseEntity<Void> eliminarOpinion(@PathVariable Integer id) {
        opinionesService.eliminarOpinion(id);
        return ResponseEntity.noContent().build();
    }
}
