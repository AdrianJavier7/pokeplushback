package com.example.pokeplushback.Repositorios;

import com.example.pokeplushback.Entidades.Productos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductosRepository extends JpaRepository<Productos, Integer> {
    //Buscar por nombre ignorando mayusculas
    List<Productos> findByNombreContainingIgnoreCase(String nombre);

    //Buscar por tipo
    @Query(value="select p from producto p where lower(p.tipo) like lower(concat('%', :tipo, '%')) ", nativeQuery = true)
    List<Productos> buscarPorTipo(@Param("tipo") String tipo);
}
