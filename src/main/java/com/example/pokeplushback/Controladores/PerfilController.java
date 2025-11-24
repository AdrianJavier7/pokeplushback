package com.example.pokeplushback.Controladores;

import com.cloudinary.Cloudinary;
import com.example.pokeplushback.Dto.UsuarioDTO;
import com.example.pokeplushback.Entidades.Usuario;
import com.example.pokeplushback.Repositorios.PerfilRepository;
import com.example.pokeplushback.Security.JWTService;
import com.example.pokeplushback.Servicios.CloudinaryService;
import com.example.pokeplushback.Servicios.PerfilService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/perfil")
public class PerfilController {
    @Autowired
    private PerfilService perfilService;

    @Autowired
    private JWTService jwtService;
    private CloudinaryService cloudinary;

    @GetMapping("/miPerfil")
    public UsuarioDTO getPerfil(@RequestHeader("Authorization") String token){
        Usuario perfilLogueado = jwtService.extraerPerfilToken(token);
        return perfilService.miPerfilDTO(perfilLogueado);
    }

    @PostMapping(value= "/updatePerfil", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public UsuarioDTO updatePerfil(@RequestHeader("Authorization") String token, @RequestPart(value = "perfil") UsuarioDTO perfilDTO, @RequestPart(value = "foto", required = false) MultipartFile foto
    ) throws Exception {
        Usuario perfilLogueado = jwtService.extraerPerfilToken(token);

        String fotoUrl = cloudinary.upload(foto.getBytes(), foto.getOriginalFilename());
        perfilDTO.setFoto(fotoUrl);
        return perfilService.updatePerfil(perfilLogueado, perfilDTO);

    }
}
