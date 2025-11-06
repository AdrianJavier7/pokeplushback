package com.example.pokeplushback.Servicios;

import com.example.pokeplushback.Dto.CarritoDTO;
import com.example.pokeplushback.Dto.ItemCarritoDTO;
import com.example.pokeplushback.Dto.ProductosDTO;
import com.example.pokeplushback.Dto.ItemDTO;
import com.example.pokeplushback.Entidades.Carrito;
import com.example.pokeplushback.Entidades.ItemsCarrito;
import com.example.pokeplushback.Entidades.Productos;
import com.example.pokeplushback.Entidades.Usuario;
import com.example.pokeplushback.Enums.Estados;
import com.example.pokeplushback.Repositorios.CarritoRepository;
import com.example.pokeplushback.Repositorios.ItemsCarritoRepository;
import com.example.pokeplushback.Repositorios.ProductosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ItemsCarritoRepository itemsCarritoRepository;

    @Autowired
    private ProductosRepository productosRepository;


    public CarritoDTO getCarritoUsuario(Usuario usuario){

        Carrito carrito = carritoRepository.findByUsuarioIdAndEstado(usuario.getId(), Estados.ACTIVO);

        return mapToDTO(carrito);
    }

    public CarritoDTO anyadirAlCarrito(ProductosDTO producto) {

        // Buscar carrito activo del usuario
        Carrito carrito = carritoRepository.findByUsuarioIdAndEstado(producto.getIdUsuario(), Estados.ACTIVO);

        if (carrito != null && carrito.getId() != null) {

            List<ItemsCarrito> items = carrito.getItems();
            List<Productos> productosEnCarrito = itemsCarritoRepository.findProductosByCarritoId(carrito.getId());

            boolean productoExiste = productosEnCarrito.stream()
                    .anyMatch(p -> p.getId().equals(producto.getId()));

            if (productoExiste) {
                // Producto ya existe → actualizar cantidad y precio
                for (ItemsCarrito item : items) {
                    if (item.getProducto().getId().equals(producto.getId())) {
                        item.setCantidad(item.getCantidad() + 1);
                        Float precioTotal = producto.getPrecio() * item.getCantidad();
                        item.setPrecioUnitario(Double.valueOf(precioTotal));
                        itemsCarritoRepository.save(item);
                        break;
                    }
                }
            } else {
                // Producto no está en el carrito → agregarlo
                ItemsCarrito nuevoItem = new ItemsCarrito();
                nuevoItem.setCantidad(1);
                nuevoItem.setCarrito(carrito);

                // ✅ Obtener producto persistido desde la base de datos
                Productos productoExistente = productosRepository.findById(producto.getId())
                        .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

                nuevoItem.setProducto(productoExistente);
                nuevoItem.setPrecioUnitario(Double.valueOf(producto.getPrecio()));
                itemsCarritoRepository.save(nuevoItem);
            }

            carrito = carritoRepository.findById(carrito.getId()).orElse(carrito);
            return mapToDTO(carrito);

        } else {
            // No existe carrito → crear uno nuevo
            Carrito nuevoCarrito = new Carrito();
            nuevoCarrito.setUsuario(new Usuario());
            nuevoCarrito.getUsuario().setId(producto.getIdUsuario());
            nuevoCarrito.setEstado(Estados.ACTIVO);
            nuevoCarrito.setCreadoEn(LocalDate.now());
            carritoRepository.save(nuevoCarrito);

            // ✅ Obtener producto persistido antes de asignarlo
            Productos productoExistente = productosRepository.findById(producto.getId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            ItemsCarrito primerItem = new ItemsCarrito();
            primerItem.setCantidad(1);
            primerItem.setCarrito(nuevoCarrito);
            primerItem.setProducto(productoExistente);
            primerItem.setPrecioUnitario(Double.valueOf(producto.getPrecio()));
            itemsCarritoRepository.save(primerItem);

            return mapToDTO(nuevoCarrito);
        }
    }


    /**
     * Convierte una entidad Carrito a CarritoDTO
     */
    private CarritoDTO mapToDTO(Carrito carrito) {
        CarritoDTO dto = new CarritoDTO();
        dto.setId(carrito.getId());
        dto.setCreadoEn(carrito.getCreadoEn());
        dto.setEstado(carrito.getEstado());
        dto.setIdUsuario(carrito.getUsuario().getId());
        List<ItemsCarrito> items = carrito.getItems();
        List<ItemCarritoDTO> itemsDTO = new ArrayList<>();
        for (ItemsCarrito item : items) {
            ItemCarritoDTO itemDTO = new ItemCarritoDTO();
            itemDTO.setId(item.getId());
            itemDTO.setCantidad(item.getCantidad());
            itemDTO.setPrecioUnitario(item.getPrecioUnitario());
            itemDTO.setIdCarrito(carrito.getId());
            itemsDTO.add(itemDTO);
        }
        dto.setItems(itemsDTO);
        return dto;
    }


    public CarritoDTO QuitarCantidadItemCarrito(ItemDTO itemDTO) {
        // Obtener los items del carrito
        Carrito carrito = carritoRepository.findById(itemDTO.getIdCarrito()).orElse(null);
        if (carrito != null) {
            List<ItemsCarrito> items = carrito.getItems();
            for (ItemsCarrito item : items) {
                if (item.getId().equals(itemDTO.getIdProducto())) {
                    item.setCantidad(item.getCantidad() - 1);
                    // Actualizar el precio unitario
                    Float precioProducto = item.getProducto().getPrecio();
                    Float precioTotal = precioProducto * item.getCantidad();
                    item.setPrecioUnitario(Double.valueOf(precioTotal));
                    itemsCarritoRepository.save(item);
                    return mapToDTO(carrito);
                }
            }
        }
        return null;
    }


    public CarritoDTO borrarItemCarrito(Integer idItem){
        Optional<ItemsCarrito> itemOpt = itemsCarritoRepository.findById(idItem);
        if (itemOpt.isPresent()) {
            ItemsCarrito item = itemOpt.get();
            Carrito carrito = item.getCarrito();
            itemsCarritoRepository.delete(item);
            return mapToDTO(carrito);
        } else {
            return null;
        }
    }
}
