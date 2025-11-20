package com.example.pokeplushback.Repositorios;

import com.example.pokeplushback.Entidades.Carrito;
import com.example.pokeplushback.Enums.Estados;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Integer> {

    // Obtener el carrito por el id del usuario
    Carrito findByUsuarioId(Integer usuarioId);

    Carrito findByUsuarioIdAndEstado(Integer usuarioId, Estados estado);

    List<Carrito> findByUsuarioIdAndEstadoIn(Integer usuarioId, List<Estados> estados);
}
