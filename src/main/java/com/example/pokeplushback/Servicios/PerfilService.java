package com.example.pokeplushback.Servicios;

import com.example.pokeplushback.Dto.DireccionDTO;
import com.example.pokeplushback.Dto.UsuarioDTO;
import com.example.pokeplushback.Entidades.Direccion;
import com.example.pokeplushback.Entidades.Usuario;
import com.example.pokeplushback.Repositorios.PerfilRepository;
import com.example.pokeplushback.Repositorios.UsuarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PerfilService {
    @Autowired
    private PerfilRepository perfilRepository;

    public UsuarioDTO miPerfilDTO(Usuario perfil){
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(perfil.getId());
        dto.setNombre(perfil.getNombre());
        dto.setApellidos(perfil.getApellidos());
        dto.setDescripcion(perfil.getDescripcion());
        dto.setEmail(perfil.getEmail());
        dto.setFechaNacimiento(perfil.getFecha_nacimiento());
        dto.setFechaRegistro(perfil.getFecha_registro());
        dto.setTelefono(perfil.getTelefono());
        dto.setFoto(perfil.getFoto());
        dto.setPassword(perfil.getPassword());
        dto.setNivel(perfil.getNivel());
        dto.setCodigoVerificacion(perfil.getCodigo_verificacion());

        List<Direccion> direcciones = perfil.getDirecciones();
        dto.setDireccion(direcciones != null && !direcciones.isEmpty() ? direccionDto(direcciones.get(0)) : null);
        return dto;
    }

    public Usuario buscarPorEmail(String email){
        return perfilRepository.findByEmail(email);
    }

    public UsuarioDTO updatePerfil(Usuario perfilLogueado, UsuarioDTO perfilDTO) {
        perfilLogueado.setNombre(perfilDTO.getNombre());
        perfilLogueado.setApellidos(perfilDTO.getApellidos());
        perfilLogueado.setDescripcion(perfilDTO.getDescripcion());
        perfilLogueado.setFecha_nacimiento(perfilDTO.getFechaNacimiento());
        perfilLogueado.setTelefono(perfilDTO.getTelefono());
        perfilLogueado.setFoto((perfilDTO.getFoto()));
        perfilLogueado.setEmail(perfilDTO.getEmail());

        if (perfilDTO.getDireccion() != null) {
            Direccion dir = direccionEntidad(perfilDTO.getDireccion());
            dir.setUsuario(perfilLogueado);
            if (perfilLogueado.getDirecciones() == null) {
                perfilLogueado.setDirecciones(new ArrayList<>());
            } else {
                perfilLogueado.getDirecciones().clear();
            }
            perfilLogueado.getDirecciones().add(dir);
        } else {
            if (perfilLogueado.getDirecciones() != null) {
                perfilLogueado.getDirecciones().clear();
            }
        }

        Usuario perfilActualizado = perfilRepository.save(perfilLogueado);
        return miPerfilDTO(perfilActualizado);
    }

    private DireccionDTO direccionDto(Direccion dir) {
        if (dir == null) return null;
        DireccionDTO dto = new DireccionDTO();
        BeanUtils.copyProperties(dir, dto);
        return dto;
    }

    private Direccion direccionEntidad(DireccionDTO dto) {
        if (dto == null) return null;
        Direccion dir = new Direccion();
        BeanUtils.copyProperties(dto, dir);
        return dir;
    }
}