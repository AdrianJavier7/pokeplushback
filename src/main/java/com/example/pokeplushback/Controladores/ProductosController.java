package com.example.pokeplushback.Controladores;

import com.example.pokeplushback.Dto.ProductosDTO;
import com.example.pokeplushback.Entidades.Opiniones;
import com.example.pokeplushback.Entidades.Productos;
import com.example.pokeplushback.Servicios.OpinionesService;
import com.example.pokeplushback.Servicios.ProductosService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
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
                .map(p -> {
                    String base64 = null;
                    if (p.getFoto() != null) {
                        byte[] fotoBytes = productosService.leerImagenDesdeOid(p.getFoto());
                        base64 = Base64.getEncoder().encodeToString(fotoBytes);
                    }
                    return new ProductosDTO(
                            p.getId(),
                            p.getNombre(),
                            p.getDescripcion(),
                            p.getPrecio(),
                            p.getTipo(),
                            base64,
                            p.getStock(),
                            p.getHabilitado()
                    );
                })
                .collect(Collectors.toList());
    }

    //Obtener producto
    @GetMapping("/{id}")
    public ResponseEntity<ProductosDTO> obtenerProducto (@PathVariable Integer id){
        Productos producto = productosService.obtenerProductoPorId(id);
        if(producto == null){
            return ResponseEntity.notFound().build();
        }

        byte[] fotoBytes = productosService.leerImagenDesdeOid(producto.getFoto());
        String base64 = Base64.getEncoder().encodeToString(fotoBytes);

        ProductosDTO productoDTO = new ProductosDTO(
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getTipo(),
                base64,
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
    @PostMapping(value ="/crear", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Productos crearProducto(@RequestPart(value="producto") ProductosDTO producto, @RequestPart(value="foto", required = false) MultipartFile foto
    ) throws Exception{

        if(foto != null && !foto.isEmpty()) {
            return productosService.crearProductosConFoto(producto, productosService.guardarFotoComoLargeObject(foto));
        } else {
            return productosService.crearProductos(producto);
        }

    }

    //Deshabilitar producto
    @PostMapping("/deshabilitar_producto/{id}")
    public Productos deshabilitarProducto(@PathVariable Integer id){
        return productosService.deshabilitarProductos(id);
    }

    @PostMapping("/obtenerPorVarios")
    public List<ProductosDTO> obtenerProductosPorIds(@RequestBody List<Integer> ids) {
        return productosService.obtenerProductosPorIds(ids);
    }
}