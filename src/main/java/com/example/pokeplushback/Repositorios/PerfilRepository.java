package com.example.pokeplushback.Repositorios;

import com.example.pokeplushback.Entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerfilRepository extends JpaRepository<Usuario, Integer> {
    Usuario findByEmail(String email);
}
