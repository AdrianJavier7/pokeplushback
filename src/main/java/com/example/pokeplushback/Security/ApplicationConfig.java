package com.example.pokeplushback.Security;

import com.example.pokeplushback.Repositorios.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UsuarioRepository usuarioRepository;

    // Metodo para cargar el usuario por su email
    @Bean
    public UserDetailsService userDetailsService() {
        return email -> usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    }

    // Metodo para autenticar al usuario
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authentication = new DaoAuthenticationProvider();
        authentication.setUserDetailsService(userDetailsService());
        authentication.setPasswordEncoder(passwordEncoder());
        return authentication;
    }

    // Metodo para encriptar la contraseña
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
