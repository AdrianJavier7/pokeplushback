package com.example.pokeplushback.Security;

import com.example.pokeplushback.Enums.Nivel;
import com.example.pokeplushback.Servicios.PerfilService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import com.example.pokeplushback.Entidades.Usuario;
import com.example.pokeplushback.Servicios.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class JWTService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Autowired
    @Lazy
    private UsuarioService usuarioService;

    @Autowired
    private PerfilService perfilService;

    // Genera un token JWT para el usuario proporcionado
    public String generateToken(Usuario usuario) {
        TokenDataDTO tokenDataDTO = TokenDataDTO.builder()
                .email(usuario.getEmail())
                .nivel(Nivel.valueOf(usuario.getNivel().name()))
                .fecha_creacion(System.currentTimeMillis())
                .fecha_expiracion(System.currentTimeMillis() + 1000 * 60 * 60 * 3)
                .build();

        return Jwts.builder()
                .claim("tokenDataDTO", tokenDataDTO)
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Extrae los datos del token JWT
    private Claims extractDatosToken(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Extrae el TokenDataDTO del token JWT
    public TokenDataDTO extractTokenData(String token) {
        Claims claims = extractDatosToken(token);
        Map<String, Object> mapa = (LinkedHashMap<String, Object>) claims.get("tokenDataDTO");

        if (mapa == null) {
            System.out.println("No se encontró tokenDataDTO en el token JWT");
            return null;
        }

        Long fechaCreacion = null;
        Long fechaExpiracion = null;
        try {
            if (mapa.get("fecha_creacion") != null)
                fechaCreacion = ((Number) mapa.get("fecha_creacion")).longValue();
            if (mapa.get("fecha_expiracion") != null)
                fechaExpiracion = ((Number) mapa.get("fecha_expiracion")).longValue();
        } catch (Exception e) {
            System.out.println("Error al parsear fechas del token: " + e.getMessage());
        }

        return TokenDataDTO.builder()
                .email((String) mapa.get("email"))
                .nivel(mapa.get("nivel") != null ? Nivel.valueOf((String) mapa.get("nivel")) : null)
                .fecha_creacion(fechaCreacion)
                .fecha_expiracion(fechaExpiracion)
                .build();
    }

    // Verifica si el token JWT ha expirado
    public boolean isExpired(String token){
        return new Date(extractTokenData(token).getFecha_expiracion()).before(new Date()) ;
    }

    // Obtiene la clave para el token JWT
    private Key getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Usuario extraerPerfilToken(String token) {
        String tokenSinCabecera = token.substring(7);
        TokenDataDTO tokenDataDTO = extractTokenData(tokenSinCabecera);

        if (tokenDataDTO == null || tokenDataDTO.getEmail() == null) {
            System.out.println("No se pudo extraer el email del token.");
            return null;
        }

        Usuario usuarioLogueado = (Usuario) usuarioService.loadUserByUsername(tokenDataDTO.getEmail());

        if (usuarioLogueado == null) {
            System.out.println("No se encontró un usuario con email: " + tokenDataDTO.getEmail());
            return null;
        }

        return perfilService.buscarPorEmail(usuarioLogueado.getEmail());
    }
}