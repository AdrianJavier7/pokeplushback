package com.example.pokeplushback.Security;

import com.example.pokeplushback.Entidades.Usuario;
import com.example.pokeplushback.Servicios.UsuarioService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JWTFilter.class);

    private final JWTService jwtService;
    private final UsuarioService usuarioService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if (request.getServletPath().contains("/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        if (token == null || token.isBlank()) {
            logger.warn("Header Authorization contiene token vacío");
            filterChain.doFilter(request, response);
            return;
        }

        TokenDataDTO tokenDataDTO;
        try {
            tokenDataDTO = jwtService.extractTokenData(token);
        } catch (JwtException | IllegalArgumentException e) {
            logger.warn("Token JWT inválido o mal formado: {}", e.getMessage());
            filterChain.doFilter(request, response);
            return;
        }

        if (tokenDataDTO != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Usuario usuario = (Usuario) usuarioService.loadUserByUsername(tokenDataDTO.getEmail());

            if (usuario == null) {
                logger.warn("Usuario no encontrado para el email: {}", tokenDataDTO.getEmail());
                filterChain.doFilter(request, response);
                return;
            }

            if (!jwtService.isExpired(token)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        usuario,
                        null,
                        usuario.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.startsWith("/auth") || path.startsWith("/api/productos");
    }
}
