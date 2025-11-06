package com.example.pokeplushback.Controladores;

import com.example.pokeplushback.Dto.UsuarioDTO;
import com.example.pokeplushback.Entidades.Usuario;
import com.example.pokeplushback.Repositorios.PerfilRepository;
import com.example.pokeplushback.Security.JWTService;
import com.example.pokeplushback.Servicios.PerfilService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/perfil")
public class PerfilController {
    @Autowired
    private PerfilService perfilService;

    @Autowired
    private JWTService jwtService;

    @GetMapping("/miPerfil")
    public UsuarioDTO getPerfil(@RequestHeader("Authorization") String token){
        Usuario perfilLogueado = jwtService.extraerPerfilToken(token);
        return perfilService.miPerfilDTO(perfilLogueado);
    }

    @PutMapping("/updatePerfil")
    public UsuarioDTO updatePerfil(@RequestHeader("Authorization") String token, @RequestBody UsuarioDTO perfilDTO) {
        Usuario perfilLogueado = jwtService.extraerPerfilToken(token);
        return perfilService.updatePerfil(perfilLogueado, perfilDTO);
    }
}
