package com.example.pokeplushback.Servicios;

import com.example.pokeplushback.Dto.ProductosDTO;
import com.example.pokeplushback.Entidades.Productos;
import com.example.pokeplushback.Repositorios.ProductosRepository;
import com.example.pokeplushback.conversores.ProductosMapper;
import org.postgresql.PGConnection;
import org.postgresql.largeobject.LargeObject;
import org.postgresql.largeobject.LargeObjectManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;

@Service
public class ProductosService {

    @Autowired
    private ProductosRepository productosRepository;

    @Autowired
    private ProductosMapper productosMapper;

    @Autowired
    private DataSource dataSource;

    //Listar todos los productos
    public List<ProductosDTO> listarProductos(){

        return productosMapper.convertirADTO(productosRepository.findAll());
    }

    //Listar productos por precio menor a mayor
    public List<ProductosDTO> listarPrecioMenor() {
        return productosMapper.convertirADTO(productosRepository.findAll(Sort.by("precio").ascending()));
    }

    //Listar productos por precio mayor a menor
    public List<ProductosDTO> listarPrecioMayor() {
        return productosMapper.convertirADTO(productosRepository.findAll(Sort.by("precio").descending()));
    }

    //Listar por orden alfabético
    public List<ProductosDTO> listarAlfabetico() {
        return productosMapper.convertirADTO(productosRepository.findAll(Sort.by(Sort.Direction.ASC, "nombre")));
    }

    //Crear producto con foto
    public Productos crearProductosConFoto(ProductosDTO dto, String fotoUrl){

        Productos producto = productosMapper.convertirAEntity(dto);
        producto.setFoto(fotoUrl);
        producto.setHabilitado(true);
        producto.setOpiniones(null);

        return productosRepository.save(producto);
    }

    //Deshabilitar producto
    public ProductosDTO deshabilitarProductos(Integer idProducto){
        Productos producto = productosRepository.findById(idProducto).orElse(null);
        if (producto == null){
            return null;
        }
        producto.setHabilitado(false);
        Productos productos = productosRepository.save(producto);
        return productosMapper.convertirADTO(productos);

    }

    //Habilitar producto
    public ProductosDTO habilitarProductos(Integer idProducto){
        Productos producto = productosRepository.findById(idProducto).orElse(null);
        if (producto == null){
            return null;
        }
        producto.setHabilitado(true);
        Productos productos = productosRepository.save(producto);
        return productosMapper.convertirADTO(productos);
    }

    //Obtener producto por ID
    public ProductosDTO obtenerProductoPorId(Integer id) {
        return productosRepository.findById(id).map(productosMapper::convertirADTO).orElse(null);
    }

    //Eliminar producto
    public void eliminarPorId(Integer id){
        productosRepository.deleteById(id);
    }

    public List<ProductosDTO> buscarPorNombre(String nombre){
        List<Productos> productos = productosRepository.findByNombreContainingIgnoreCase(nombre);
        return productosMapper.convertirADTO(productos);
    }

    //Obtener un producto por su id
    public List<ProductosDTO> obtenerProductosPorIds(List<Integer> ids) {
        return productosMapper.convertirADTO(productosRepository.findAllById(ids));
    }

    //Añadir stock
    public ProductosDTO anadirStock(Integer idProducto, Integer cantidad){
        Optional<Productos> productoOptional = productosRepository.findById(idProducto);
        if (productoOptional.isPresent()){
            Productos producto = productoOptional.get();
            producto.setStock(producto.getStock()+cantidad);
            productosRepository.save(producto);
            return productosMapper.convertirADTO(producto);
        }
        return null;
    }

    //Editar producto
    public Productos editarProducto(Integer id, ProductosDTO dto, String fotoUrl){
        Productos producto = productosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        producto.setTipo(dto.getTipo());
        producto.setTipo2(dto.getTipo2());

        if (fotoUrl != null){
            producto.setFoto(fotoUrl);
        }

        return productosRepository.save(producto);
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
