package com.example.pokeplushback.Repositorios;

import com.example.pokeplushback.Entidades.Opiniones;
import com.example.pokeplushback.Entidades.Productos;
import com.example.pokeplushback.Entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OpinionesRepository extends JpaRepository<Opiniones, Integer> {

    //                ----------ESTO SON BUSQUEDAS/LECTURAS------------------


    //Lo buscamos por el id del usuario a la opinion (todas las opiniones de ese usuario)
    List<Opiniones> findByUsuario(Usuario usuario);

    //Lo buscamos por las opiniones del producto en especifico (su id)
    List<Opiniones> findByProducto(Productos producto);

    //Lo buscamos por el id del producto (todas las opiniones de ese producto y de esa manera no se tiene que cargar la entidad del producto)
    List<Opiniones> findByProductosId(Integer idProducto);









}
