package com.example.pokeplushback.Security;

import com.example.pokeplushback.Entidades.Usuario;
import com.example.pokeplushback.Servicios.UsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final UsuarioService usuarioService;

    // Metodo que se ejecuta una vez por cada solicitud HTTP entrante
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
        TokenDataDTO tokenDataDTO = jwtService.extractTokenData(token);

        if (tokenDataDTO != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Usuario usuario = (Usuario) usuarioService.loadUserByUsername(tokenDataDTO.getEmail());

            if (usuario == null) {
                System.out.println("Usuario no encontrado para el email: " + tokenDataDTO.getEmail());
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
}
