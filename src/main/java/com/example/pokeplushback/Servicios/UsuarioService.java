package com.example.pokeplushback.Servicios;

import com.example.pokeplushback.Dto.LoginDTO;
import com.example.pokeplushback.Dto.RegistroDTO;
import com.example.pokeplushback.Dto.RespuestaDTO;
import com.example.pokeplushback.Entidades.Usuario;
import com.example.pokeplushback.Enums.Nivel;
import com.example.pokeplushback.Repositorios.UsuarioRepository;
import com.example.pokeplushback.Security.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UsuarioService implements UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTService jwtService;

    // Metodo para cargar un usuario por su email
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(email).orElse(null);
    }

    // Metodo para registrar un nuevo usuario
    public Usuario registrarUsuario(RegistroDTO dto){
        Optional<Usuario> existente = usuarioRepository.findByEmail(dto.getEmail());
        if (existente.isPresent()) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setEmail(dto.getEmail());
        nuevoUsuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        nuevoUsuario.setNivel(Nivel.USUARIO);
        nuevoUsuario.setFecha_registro(new Date());
        nuevoUsuario.setVerificado(false);

        Usuario usuarioGuardado = usuarioRepository.save(nuevoUsuario);
        return usuarioGuardado;
    }

    // Metodo para verificar a un usuario y generar su token JWT
    public ResponseEntity<RespuestaDTO> login(LoginDTO dto) {
        Optional<Usuario> usuarioOpcional = usuarioRepository.findByEmail(dto.getEmail());

        if (usuarioOpcional.isPresent()) {
            Usuario usuario = usuarioOpcional.get();

            if (passwordEncoder.matches(dto.getPassword(), usuario.getPassword())) {

                String token = jwtService.generateToken(usuario);
                return ResponseEntity
                        .ok(RespuestaDTO
                                .builder()
                                .estado(HttpStatus.OK.value())
                                .token(token).build());
            } else {
                throw new BadCredentialsException("Contraseña incorrecta");
            }
        } else {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
    }
}
