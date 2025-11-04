package com.example.pokeplushback.Repositorios;

import com.example.pokeplushback.Entidades.Productos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductosRepository extends JpaRepository<Productos, Integer> {
    //Buscar por nombre ignorando mayusculas
    List<Productos> findByNombreContainingIgnoreCase(String nombre);


}
