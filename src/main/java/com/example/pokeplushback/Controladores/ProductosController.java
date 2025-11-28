package com.example.pokeplushback.Controladores;

import com.example.pokeplushback.Dto.OpinionesDTO;
import com.example.pokeplushback.Dto.ProductosDTO;
import com.example.pokeplushback.Entidades.Opiniones;
import com.example.pokeplushback.Entidades.Productos;
import com.example.pokeplushback.Servicios.CloudinaryService;
import com.example.pokeplushback.Servicios.OpinionesService;
import com.example.pokeplushback.Servicios.ProductosService;
import com.example.pokeplushback.conversores.ProductosMapper;
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
    private CloudinaryService cloudinaryService;
    @Autowired
    private ProductosMapper productosMapper;

    //Listar productos
    @GetMapping
    public List<ProductosDTO> getProductos(){
        return productosService.listarProductos();
    }

    //Obtener producto por id
    @GetMapping("/{id}")
    public ResponseEntity<ProductosDTO> obtenerProducto (@PathVariable Integer id){
        ProductosDTO productoDTO = productosService.obtenerProductoPorId(id);
        if(productoDTO == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productoDTO);
    }

    //Listar por precios
    @GetMapping("/orden/precio/desc")
    public List<ProductosDTO> listarPrecioMayor() {
        return productosService.listarPrecioMayor();
    }

    @GetMapping("/orden/precio/asc")
    public List<ProductosDTO> listarPrecioMenor() {
        return productosService.listarPrecioMenor();
    }

    @GetMapping("/orden/alfabetico")
    public List<ProductosDTO> listarAlfabetico() {
        return productosService.listarAlfabetico();
    }

    //Listar las opiniones de producto, las más recientes primeros
    @GetMapping("/{id}/opiniones")
    public List<OpinionesDTO> listarOpinionesProducto(@PathVariable Integer id) {
        return opinionesService.listarOpinionesPorProducto(id);
    }

    //Crear producto
    @PostMapping(value ="/crear", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ProductosDTO crearProducto(@RequestPart(value="producto") ProductosDTO producto, @RequestPart(value="foto", required = false) MultipartFile foto
    ) throws Exception {
        String fotoUrl = cloudinaryService.upload(foto.getBytes(), "producto_" + producto.getNombre());

        Productos entidad = productosService.crearProductosConFoto(producto, fotoUrl);
        return productosMapper.convertirADTO(entidad);
    }

    //Deshabilitar producto
    @PostMapping("/deshabilitar_producto/{id}")
    public ResponseEntity<ProductosDTO> deshabilitarProducto(@PathVariable Integer id){
        ProductosDTO productosDTO = productosService.deshabilitarProductos(id);
        if (productosDTO == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productosDTO);
    }

    @PostMapping("/habilitar_producto/{id}")
    public ResponseEntity<ProductosDTO> habilitarProducto(@PathVariable Integer id) {
        ProductosDTO productosDTO = productosService.habilitarProductos(id);
        if (productosDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productosDTO);
    }

    @PostMapping("/obtenerPorVarios")
    public List<ProductosDTO> obtenerProductosPorIds(@RequestBody List<Integer> ids) {
        return productosService.obtenerProductosPorIds(ids);
    }

    @GetMapping ("/buscar")
    public List<ProductosDTO> buscarProductosPorNombre(@RequestParam String nombre){
        return productosService.buscarPorNombre(nombre);
    }
}