package com.example.pokeplushback.Servicios;

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
}
