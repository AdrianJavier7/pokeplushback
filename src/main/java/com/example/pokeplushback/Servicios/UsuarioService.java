package com.example.pokeplushback.Servicios;

import com.example.pokeplushback.Dto.*;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.postgresql.largeobject.LargeObjectManager;
import org.postgresql.largeobject.LargeObject;
import org.postgresql.PGConnection;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Date;
import java.util.Optional;
import java.util.Random;


@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private EmailService emailService;

    // Metodo para cargar un usuario por su email
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(email).orElse(null);
    }

    // Metodo para registrar un nuevo usuario
    public Usuario registrarUsuarioConFoto(RegistroDTO dto){
        Optional<Usuario> existente = usuarioRepository.findByEmail(dto.getEmail());
        if (existente.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El correo ya está registrado");
        }

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setEmail(dto.getEmail());
        nuevoUsuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        nuevoUsuario.setNivel(Nivel.USUARIO);
        nuevoUsuario.setFecha_registro(new Date());
        nuevoUsuario.setVerificado(false);
        nuevoUsuario.setFoto(null);

        Usuario usuarioGuardado = usuarioRepository.save(nuevoUsuario);
        return usuarioGuardado;
    }

    public Usuario registrarUsuario(RegistroDTO dto){
        Optional<Usuario> existente = usuarioRepository.findByEmail(dto.getEmail());
        if (existente.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El correo ya está registrado");
        }

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setEmail(dto.getEmail());
        nuevoUsuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        nuevoUsuario.setNivel(Nivel.USUARIO);
        nuevoUsuario.setFoto(null);
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

            if (!usuario.isVerificado()) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cuenta no verificada");
            }

            if (passwordEncoder.matches(dto.getPassword(), usuario.getPassword())) {
                String token = jwtService.generateToken(usuario);
                return ResponseEntity
                        .ok(RespuestaDTO
                                .builder()
                                .estado(HttpStatus.OK.value())
                                .token(token)
                                .rol(usuario.getNivel().name()).build());
            } else {
                throw new BadCredentialsException("Contraseña incorrecta");
            }
        } else {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
    }

    // Metodo de bienvenida y envio de codigo de verificacion
    public ResponseEntity<String> enviarCodigoVerificacion(Usuario user) {
        Optional<Usuario> opt = usuarioRepository.findByEmail(user.getEmail());
        if (!opt.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No existe un usuario con ese correo");
        }

        Usuario existente = opt.get();
        String codigo = generarCodigoNumerico(6);
        existente.setCodigo_verificacion(codigo);
        usuarioRepository.save(existente);

        String bienvenida = "¡Nos alegra muchísimo tenerte en nuestra comunidad de peluches Pokémon! 🎉 PokePush es el lugar ideal para descubrir, compartir y disfrutar los peluches más adorables, conectar con otros entrenadores y llenar tu colección con ternura. 💖\n"
                + "\n"
                + "Aquí puedes:\n"
                + "🧸 Explorar miles de peluches compartidos por otros usuarios.\n"
                + "❤️ Guardar tus favoritos y organizarlos fácilmente.\n"
                + "💬 Interactuar con otros fans dejando comentarios y valoraciones.\n"
                + "\n"
                + "Para comenzar, te recomendamos:\n"
                + "🔹 Completar tu perfil para que la comunidad te conozca mejor.\n"
                + "\n"
                + "Si necesitas ayuda, estamos aquí para ti. No dudes en visitar nuestra sección de ayuda o escribirnos.\n"
                + "\n"
                + "¡Esperamos ver tu increíble colección pronto! 🪶🧸✨\n"
                + "\n"
                + "Saludos,\n"
                + "El equipo de PokePush ⚡🧸💫\n";

        String mensaje = bienvenida + "\nTu código de verificación es: " + codigo;

        emailService.enviarEmail(existente.getEmail(), "¡Bienvenido/a a PokePush! 🧸⚡", mensaje);
        return ResponseEntity.ok("Correo enviado con código de verificación");
    }

    // Metodo para verificar el codigo de verificacion
    public ResponseEntity<String> verificarCodigo(VerificacionDTO dto) {
        Optional<Usuario> opt = usuarioRepository.findByEmail(dto.getEmail());
        if (!opt.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario no encontrado");
        }

        Usuario u = opt.get();
        String codigoAlmacenado = u.getCodigo_verificacion();
        if (codigoAlmacenado != null && codigoAlmacenado.equals(dto.getCodigo())) {
            u.setCodigo_verificacion(null);
            u.setVerificado(true);
            usuarioRepository.save(u);
            return ResponseEntity.ok("Código verificado correctamente");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Código inválido");
    }

    // Metodo para enviar el codigo de recuperacion de contraseña
    public ResponseEntity<String> enviarCodigoRecuperacion(String email) {
        Optional<Usuario> opt = usuarioRepository.findByEmail(email);
        if (!opt.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No existe un usuario con ese correo");
        }

        Usuario u = opt.get();
        String codigo = generarCodigoNumerico(6);
        u.setCodigo_verificacion(codigo);
        usuarioRepository.save(u);

        String mensaje = "Has solicitado recuperar tu contraseña.\nTu código de recuperación es: " + codigo +
                "\nUsa este código para cambiar tu contraseña.";
        emailService.enviarEmail(u.getEmail(), "Recuperación de contraseña - PokePush", mensaje);

        return ResponseEntity.ok("Correo enviado con código de recuperación");
    }

    // Meto para cambiar la contraseña usando el código de recuperación
    public ResponseEntity<String> cambiarContrasena(CambiarContrasenaDTO dto) {
        Optional<Usuario> opt = usuarioRepository.findByEmail(dto.getEmail());
        if (!opt.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario no encontrado");
        }

        Usuario u = opt.get();
        String codigo = u.getCodigo_verificacion();
        if (codigo == null || !codigo.equals(dto.getCodigo())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Código inválido");
        }

        u.setPassword(passwordEncoder.encode(dto.getNuevaPassword()));
        u.setCodigo_verificacion(null);
        usuarioRepository.save(u);

        return ResponseEntity.ok("Contraseña actualizada correctamente");
    }

    // Metodo para generar un codigo numerico aleatorio de una longitud dada
    private String generarCodigoNumerico(int longitud) {
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < longitud; i++) {
            sb.append(rnd.nextInt(10));
        }
        return sb.toString();
    }

    // Guardar una foto como Large Object en PostgreSQL y devolver su OID
    public Long guardarFotoComoLargeObject(MultipartFile file) throws Exception {
        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);
            LargeObjectManager lobj = conn.unwrap(PGConnection.class).getLargeObjectAPI();

            long oid = lobj.createLO(LargeObjectManager.WRITE);
            LargeObject obj = lobj.open(oid, LargeObjectManager.WRITE);
            obj.write(file.getBytes());
            obj.close();

            conn.commit();
            return oid;
        }
    }

    // Leer una imagen desde un OID de Large Object en PostgreSQL
    public byte[] leerImagenDesdeOid(Long oid) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            // Usamos PGConnection para acceder a LargeObjectManager, que no viene por defecto en JDBC
            org.postgresql.PGConnection pgConn = connection.unwrap(org.postgresql.PGConnection.class);
            LargeObjectManager lobj = pgConn.getLargeObjectAPI();

            LargeObject obj = lobj.open(oid, LargeObjectManager.READ);
            byte[] data = new byte[obj.size()];
            obj.read(data, 0, obj.size());
            obj.close();

            connection.commit();
            return data;
        } catch (Exception e) {
            throw new RuntimeException("Error leyendo imagen por OID", e);
        }
    }
}
