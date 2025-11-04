package com.example.pokeplushback.Repositorios;

import com.example.pokeplushback.Entidades.Opiniones;
import com.example.pokeplushback.Entidades.Productos;
import com.example.pokeplushback.Entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OpinionesRepository extends JpaRepository<Opiniones, Integer> {


}
