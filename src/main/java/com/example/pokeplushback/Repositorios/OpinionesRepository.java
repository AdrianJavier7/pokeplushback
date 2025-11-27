package com.example.pokeplushback.Repositorios;

import com.example.pokeplushback.Entidades.Opiniones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OpinionesRepository extends JpaRepository<Opiniones, Integer> {

    List<Opiniones> findByProductoId(Integer idProducto);
}
