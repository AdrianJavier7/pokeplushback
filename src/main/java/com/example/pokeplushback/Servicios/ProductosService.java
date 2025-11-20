package com.example.pokeplushback.Servicios;

import com.example.pokeplushback.Dto.ProductosDTO;
import com.example.pokeplushback.Entidades.ItemsCarrito;
import com.example.pokeplushback.Entidades.Opiniones;
import com.example.pokeplushback.Entidades.Productos;
import com.example.pokeplushback.Enums.Tipos;
import com.example.pokeplushback.Repositorios.ProductosRepository;
import org.postgresql.PGConnection;
import org.postgresql.largeobject.LargeObject;
import org.postgresql.largeobject.LargeObjectManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductosService {

    @Autowired
    private ProductosRepository productosRepository;

    @Autowired
    private DataSource dataSource;

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

    public Productos crearProductosConFoto(ProductosDTO producto, Long oid){

        Productos productoNuevo =  new Productos();
        productoNuevo.setNombre(producto.getNombre());
        productoNuevo.setDescripcion(producto.getDescripcion());
        productoNuevo.setPrecio(producto.getPrecio());
        productoNuevo.setTipo(producto.getTipo());
        productoNuevo.setFoto(oid);
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

    public Productos obtenerProductoPorId(Integer id) {
        return productosRepository.findById(id).orElse(null);
    }

    public List<ProductosDTO> obtenerProductosPorIds(List<Integer> ids) {

        List<Productos> productos = productosRepository.findAllById(ids);
        List<ProductosDTO> productosDTO = new ArrayList<ProductosDTO>();

        for (Productos producto : productos) {
            ProductosDTO dto = new ProductosDTO();
            dto.setId(producto.getId());
            dto.setNombre(producto.getNombre());
            dto.setDescripcion(producto.getDescripcion());
            dto.setPrecio(producto.getPrecio());
            dto.setTipo(producto.getTipo());
            

            byte[] fotoBytes = leerImagenDesdeOid(producto.getFoto());
            String base64 = java.util.Base64.getEncoder().encodeToString(fotoBytes);
            dto.setFoto(base64);

            dto.setStock(producto.getStock());
            dto.setHabilitado(producto.getHabilitado());
            productosDTO.add(dto);
        }

        return productosDTO;
    }

    // Guardar una foto como Large Object en PostgreSQL y devolver su OID
    public Long guardarFotoComoLargeObject(MultipartFile file) throws Exception {
        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);
            LargeObjectManager lobj = conn.unwrap(PGConnection.class).getLargeObjectAPI();

            long oid = lobj.createLO(LargeObjectManager.WRITE);
            LargeObject obj = lobj.open(oid, LargeObjectManager.WRITE);
            obj.write(file.getBytes());
            obj.close();

            conn.commit();
            return oid;
        }
    }

    // Leer una imagen desde un OID de Large Object en PostgreSQL
    public byte[] leerImagenDesdeOid(Long oid) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            // Usamos PGConnection para acceder a LargeObjectManager, que no viene por defecto en JDBC
            org.postgresql.PGConnection pgConn = connection.unwrap(org.postgresql.PGConnection.class);
            LargeObjectManager lobj = pgConn.getLargeObjectAPI();

            LargeObject obj = lobj.open(oid, LargeObjectManager.READ);
            byte[] data = new byte[obj.size()];
            obj.read(data, 0, obj.size());
            obj.close();

            connection.commit();
            return data;
        } catch (Exception e) {
            throw new RuntimeException("Error leyendo imagen por OID", e);
        }
    }

}
