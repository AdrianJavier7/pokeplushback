package com.example.pokeplushback.Repositorios;

import com.example.pokeplushback.Entidades.ItemsCarrito;
import com.example.pokeplushback.Entidades.Productos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemsCarritoRepository extends JpaRepository<ItemsCarrito, Integer> {

    @Query("SELECT ic.producto FROM ItemsCarrito ic WHERE ic.carrito.id = :carritoId")
    List<Productos> findProductosByCarritoId(@Param("carritoId") Integer carritoId);


}