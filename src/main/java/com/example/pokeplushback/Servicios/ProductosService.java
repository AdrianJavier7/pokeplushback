package com.example.pokeplushback.Servicios;

import com.example.pokeplushback.Dto.ProductosDTO;
import com.example.pokeplushback.Entidades.ItemsCarrito;
import com.example.pokeplushback.Entidades.Opiniones;
import com.example.pokeplushback.Entidades.Productos;
import com.example.pokeplushback.Repositorios.ProductosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    public Productos crearProductos(ProductosDTO producto){

        Productos productoNuevo =  new Productos();
        productoNuevo.setNombre(producto.getNombre());
        productoNuevo.setDescripcion(producto.getDescripcion());
        productoNuevo.setPrecio(producto.getPrecio());
        productoNuevo.setTipo(producto.getTipo());
        productoNuevo.setFoto(null);
        productoNuevo.setStock(producto.getStock());
        productoNuevo.setHabilitado(true);
        productoNuevo.setOpiniones(null);

        return productosRepository.save(productoNuevo);
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

    //Habilitar producto
    public Productos habilitarProductos(Integer idProducto){
        Productos producto = productosRepository.findById(idProducto).orElse(null);

        if (producto == null){
            return null;
        }

        producto.setHabilitado(true);

        return productosRepository.save(producto);

    }
}
