package com.example.pokeplushback.Controladores;

import com.example.pokeplushback.Dto.*;
import com.example.pokeplushback.Entidades.Usuario;
import com.example.pokeplushback.Security.JWTService;
import com.example.pokeplushback.Servicios.EmailService;
import com.example.pokeplushback.Servicios.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private UsuarioService service;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JWTService jwtService;

    // Endpoint para el registro de usuarios
    @PostMapping(value ="/registro")
    public Usuario registro(@RequestBody RegistroDTO registroDTO) {

        return service.registrarUsuario(registroDTO);
    }

    // Endpoint para el login de usuarios
    @PostMapping("/login")
    public ResponseEntity<RespuestaDTO> registro(@RequestBody LoginDTO dto){
        return service.login(dto);
    }

    // Endpoint para enviar el correo de verificacion
    @PostMapping("/enviarEmail")
    public ResponseEntity<String> enviarCorreo(@RequestBody Usuario user) {
        return service.enviarCodigoVerificacion(user);
    }

    // Endpoint para verificar el codigo de verificacion
    @PostMapping("/verificar")
    public ResponseEntity<String> verificarCodigo(@RequestBody VerificacionDTO dto) {
        return service.verificarCodigo(dto);
    }

    // Endpoint para enviar el codigo de recuperacion de contraseña
    @PostMapping("/recuperar/enviarCodigo")
    public ResponseEntity<String> enviarCodigoRecuperacion(@RequestBody Usuario user) {
        return service.enviarCodigoRecuperacion(user.getEmail());
    }

    // Endpoint para cambiar la contraseña
    @PostMapping("/recuperar/cambiar")
    public ResponseEntity<String> cambiarContrasena(@RequestBody CambiarContrasenaDTO dto) {
        return service.cambiarContrasena(dto);
    }

}
