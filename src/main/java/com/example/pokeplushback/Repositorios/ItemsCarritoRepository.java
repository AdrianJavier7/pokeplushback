package com.example.pokeplushback.Repositorios;

import com.example.pokeplushback.Entidades.ItemsCarrito;
import com.example.pokeplushback.Entidades.Productos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemsCarritoRepository extends JpaRepository<ItemsCarrito, Integer> {

    // Obtener todos los productos del carrito por el id del carrito
    List<Productos> findProductosByCarritoId(Integer carritoId);

}