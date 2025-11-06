package com.example.pokeplushback.Servicios;

import com.example.pokeplushback.Dto.UsuarioDTO;
import com.example.pokeplushback.Entidades.Usuario;

public interface IPerfilService {
    UsuarioDTO updatePerfil(Usuario perfilLogueado, UsuarioDTO usuarioDTO);
}
