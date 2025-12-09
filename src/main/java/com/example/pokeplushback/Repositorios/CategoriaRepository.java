/*
package com.example.pokeplushback.Repositorios;

import com.example.pokeplushback.Entidades.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    // Consulta nativa (ejemplo)
    @Query(value = "SELECT * FROM categorias WHERE nombre = :nombre", nativeQuery = true)
    List<Categoria> findByNombreNative(@Param("nombre") String nombre);
}
*/