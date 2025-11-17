package com.example.pokeplushback.Controladores;

import com.example.pokeplushback.Dto.ProductosDTO;
import com.example.pokeplushback.Entidades.Opiniones;
import com.example.pokeplushback.Entidades.Productos;
import com.example.pokeplushback.Servicios.OpinionesService;
import com.example.pokeplushback.Servicios.ProductosService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/productos")
@AllArgsConstructor
public class ProductosController {

    @Autowired
    private final OpinionesService opinionesService;
    private ProductosService productosService;

    //Listar productos
    @GetMapping
    public List<ProductosDTO> getProductos(){

        return productosService.listarProductos().stream()
                .map(p -> new ProductosDTO(
                        p.getId(),
                        p.getNombre(),
                        p.getDescripcion(),
                        p.getPrecio(),
                        p.getTipo(),
                        null, // foto
                        p.getStock(),
                        p.getHabilitado()
                )).collect(Collectors.toList());
    }

    //Obtener producto
    @GetMapping("/{id}")
    public ResponseEntity<ProductosDTO> obtenerProducto (@PathVariable Integer id){
        Productos producto = productosService.obtenerProductoPorId(id);
        if(producto == null){
            return ResponseEntity.notFound().build();
        }

        ProductosDTO productoDTO = new ProductosDTO(
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getTipo(),
                null, // foto
                producto.getStock(),
                producto.getHabilitado()
        );
        return ResponseEntity.ok(productoDTO);
    }

    //Listar por precios
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