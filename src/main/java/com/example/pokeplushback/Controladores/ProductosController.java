package com.example.pokeplushback.Controladores;

import com.example.pokeplushback.Entidades.Opiniones;
import com.example.pokeplushback.Entidades.Productos;
import com.example.pokeplushback.Repositorios.ProductosRepository;
import com.example.pokeplushback.Servicios.OpinionesService;
import com.example.pokeplushback.Servicios.ProductosService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/productos")
@AllArgsConstructor
public class ProductosController {

    private final OpinionesService opinionesService;
    private ProductosService productosService;

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
}
