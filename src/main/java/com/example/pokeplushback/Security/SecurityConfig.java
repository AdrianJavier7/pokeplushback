package com.example.pokeplushback.Security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final JWTFilter jwtFilterChain;
    private final AuthenticationProvider authenticationProvider;

    // Maneja los accesos denegados
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    // Configura la cadena de filtros de seguridad
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> {
                    CorsConfiguration configuration = new CorsConfiguration();
                    configuration.addAllowedOriginPattern("*"); // Permite cualquier otro origen
                    configuration.addAllowedMethod("*"); // Permite todos los métodos (GET, POST, etc.)
                    configuration.addAllowedHeader("*"); // Permite todos los headers
                    configuration.setAllowCredentials(true); // Permite enviar cookies o credenciales

                    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                    source.registerCorsConfiguration("/**", configuration);
                    cors.configurationSource(source);
                })
                .authorizeHttpRequests(req -> req
                        // rutas públicas
                        .requestMatchers("/auth/**", "/error").permitAll()
                        .requestMatchers("/api/productos",
                                "/carrito/verPedidos",
                                "/api/productos",
                                "/api/productos/orden/**",
                                "/api/productos/buscar",
                                "/api/productos/obtenerPorVarios",
                                "/api/productos/*/opiniones").permitAll()

                        // rutas restringidas a ADMIN
                        .requestMatchers("/api/productos/crear",
                                "/api/productos/editar/**",
                                "/api/productos/eliminar/**",
                                "/api/productos/deshabilitar_producto/**",
                                "/api/productos/habilitar_producto/**",
                                "/api/productos/*/stock",
                                "/carrito/eliminarPedidos/**").hasAuthority("ADMIN")
                        .anyRequest().authenticated())
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtFilterChain, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling((exception) -> exception.accessDeniedHandler(accessDeniedHandler()));

        return http.build();
    }

    // Configura la fuente de configuración CORS
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*"); // Permite cualquier origen
        configuration.addAllowedMethod("*"); // Permite todos los métodos (GET, POST, etc.)
        configuration.addAllowedHeader("*"); // Permite todos los headers
        configuration.setAllowCredentials(true); // Permite enviar cookies o credenciales

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
