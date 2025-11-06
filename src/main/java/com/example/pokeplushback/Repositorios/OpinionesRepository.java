package com.example.pokeplushback.Repositorios;

import com.example.pokeplushback.Entidades.Opiniones;
import com.example.pokeplushback.Entidades.Productos;
import com.example.pokeplushback.Entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OpinionesRepository extends JpaRepository<Opiniones, Integer> {

    //Lista todas las opiniones del producto, de la más reciente a la más antigua
    List<Opiniones> findByProductoIdOrderByIdDesc (Integer idProducto);

    // buscar por id del usuario
    List<Opiniones> findByUsuarioId(Integer usuarioId);

}
