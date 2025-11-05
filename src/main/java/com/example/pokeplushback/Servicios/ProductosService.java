package com.example.pokeplushback.Servicios;

import com.example.pokeplushback.Entidades.ItemsCarrito;
import com.example.pokeplushback.Entidades.Opiniones;
import com.example.pokeplushback.Entidades.Productos;
import com.example.pokeplushback.Repositorios.ProductosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductosService {

    @Autowired
    private ProductosRepository productosRepository;

    //Listar todos los productos
    public List<Productos> listarProductos(){
        return productosRepository.findAll();
    }

    //Listar productos por precio menor a mayor
    public List<Productos> listarPrecioMenor() {
        return productosRepository.findAll(Sort.by("precio").ascending());
    }

    //Listar productos por precio mayor a menor
    public List<Productos> listarPrecioMayor() {
        return productosRepository.findAll(Sort.by("precio").descending());
    }

    //Relación con opiniones y carrito
    public Productos guardarProductos(Productos producto){
        if (producto.getOpiniones() != null){
            for(Opiniones opiniones : producto.getOpiniones()){
                opiniones.setProducto(producto);
            }
        }

        return productosRepository.save(producto);
    }

    //Añadir producto
    public Productos añadirProductos(Productos producto){
        producto.setOpiniones(null);

        return productosRepository.save(producto);
    }

    //Deshabilitar producto
    public Productos deshabilitarProductos(Integer idProducto){
        Productos producto = productosRepository.findById(idProducto).orElse(null);

        if (producto == null){
            return null;
        }

        producto.setHabilitado(false);

        return productosRepository.save(producto);

    }
}
