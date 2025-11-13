package com.example.pokeplushback.Controladores;

import com.example.pokeplushback.Dto.ProductosDTO;
import com.example.pokeplushback.Entidades.Opiniones;
import com.example.pokeplushback.Entidades.Productos;
import com.example.pokeplushback.Servicios.OpinionesService;
import com.example.pokeplushback.Servicios.ProductosService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
@AllArgsConstructor
public class ProductosController {

    @Autowired
    private final OpinionesService opinionesService;
    private ProductosService productosService;

    @GetMapping
    public List<Productos> getProductos(){
        return productosService.listarProductos();
    }

    @GetMapping("/precio/desc")
    public List<Productos> listarPrecioMayor() {
        return productosService.listarPrecioMayor();
    }

    @GetMapping("/precio/asc")
    public List<Productos> listarPrecioMenor() {
        return productosService.listarPrecioMenor();
    }

    //Listar las opiniones de producto, las más recientes primeros
    @GetMapping("/{id}/opiniones")
    public List<Opiniones> listarOpinionesProducto(@PathVariable Integer id) {
        return opinionesService.listarOpinionesPorProducto(id);
    }

    //Crear producto
    @PostMapping("/crear_producto")
    public Productos crearProducto(@RequestBody ProductosDTO producto){
        return productosService.crearProductos(producto);


    }

    //Deshabilitar producto
    @PostMapping("/deshabilitar_producto/{id}")
    public Productos deshabilitarProducto(@PathVariable Integer id){
        return productosService.deshabilitarProductos(id);
    }

}