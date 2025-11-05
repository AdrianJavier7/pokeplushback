package com.example.pokeplushback.Repositorios;

import com.example.pokeplushback.Entidades.Opiniones;
import com.example.pokeplushback.Entidades.Productos;
import com.example.pokeplushback.Entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OpinionesRepository extends JpaRepository<Opiniones, Integer> {

    //Lista todas las opiniones del producto, de la más reciente a la más antigua
    List<Opiniones> findByProductoIdOrderByIdDesc (Integer idProducto);

}
