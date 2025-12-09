/*
package com.example.pokeplushback.Controladores;

import com.example.pokeplushback.Dto.CategoriaDTO;
import com.example.pokeplushback.Dto.OpinionesDTO;
import com.example.pokeplushback.Entidades.Usuario;
import com.example.pokeplushback.Security.JWTService;
import com.example.pokeplushback.Servicios.CategoriaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categoria")
@AllArgsConstructor
public class CategoriaController {

    private CategoriaService categoriaService;

    private JWTService jwtService;

    @PostMapping(value = {"/crearCategoria", "/crearCategoria/"})
    public ResponseEntity<CategoriaDTO> crear(@RequestBody CategoriaDTO dto) {
        CategoriaDTO creado = categoriaService.crearCategoria(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping(value = {"/verTodasCategorias", "/verTodasCategorias/"})
    public ResponseEntity<List<CategoriaDTO>> verTodas() {
        return ResponseEntity.ok(categoriaService.verTodasCategorias());
    }

    @GetMapping(value = {"/verUnaCategoria/{id}", "/verUnaCategoria/{id}"})
    public ResponseEntity<CategoriaDTO> verUna(@PathVariable Integer id) {
        return ResponseEntity.ok(categoriaService.verUnaCategoria(id));
    }

    @PutMapping(value = {"/editarCategoria/{id}", "/editarCategoria/{id}"})
    public ResponseEntity<CategoriaDTO> editarCategoria(@PathVariable Integer id, @RequestBody CategoriaDTO dto) {
        return ResponseEntity.ok(categoriaService.editarCategoria(id, dto));
    }

    @DeleteMapping(value = {"/eliminarCategoria/{id}", "/eliminarCategoria/{id}"})
    public ResponseEntity<Void> eliminarCategoria(@PathVariable Integer id) {
        categoriaService.eliminarCategoria(id);
        return ResponseEntity.noContent().build();
    }
}
*/