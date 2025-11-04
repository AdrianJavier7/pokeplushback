package com.example.pokeplushback.Servicios;

import com.example.pokeplushback.Dto.ProductosDTO;
import com.example.pokeplushback.Entidades.Carrito;
import com.example.pokeplushback.Entidades.ItemsCarrito;
import com.example.pokeplushback.Entidades.Productos;
import com.example.pokeplushback.Entidades.Usuario;
import com.example.pokeplushback.Enums.Estados;
import com.example.pokeplushback.Repositorios.CarritoRepository;
import com.example.pokeplushback.Repositorios.ItemsCarritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ItemsCarritoRepository itemsCarritoRepository;


    public Carrito getCarritoUsuario(Usuario usuario){

        return carritoRepository.findByUsuarioId(usuario.getId());

    }

    public Carrito anyadirAlCarrito(ProductosDTO producto){

        Carrito carrito = carritoRepository.findByUsuarioIdAndEstado(producto.getIdUsuario(), Estados.ACTIVO);

        if (carrito.getId() != null) {
            List<ItemsCarrito> items = carrito.getItems();

            List<Productos> productosEnCarrito = itemsCarritoRepository.findProductosByCarritoId(carrito.getId());

            ItemsCarrito nuevoItem = new ItemsCarrito();
            nuevoItem.setCantidad(1);

            for (Productos p : productosEnCarrito) {
                Float precioProducto = p.getPrecio();
                Float precioTotal;

                if (p.getId().equals(producto.getId())) {
                    // El producto ya está en el carrito, actualizar la cantidad
                    for (ItemsCarrito item : items) {
                        if (item.getProducto().getId().equals(producto.getId())) {
                            item.setCantidad(item.getCantidad() + 1);
                            precioTotal = precioProducto * item.getCantidad();
                            item.setPrecioUnitario(Double.valueOf(precioTotal));
                            itemsCarritoRepository.save(item);
                            return carrito;
                        } else {
                            // El producto no está en el carrito, agregar nuevo item
                            nuevoItem.setCarrito(carrito);
                            Productos productoNuevo = new Productos();
                            productoNuevo.setId(producto.getId());
                            nuevoItem.setProducto(productoNuevo);
                            precioTotal = precioProducto * nuevoItem.getCantidad();
                            nuevoItem.setPrecioUnitario(Double.valueOf(precioTotal));
                            itemsCarritoRepository.save(nuevoItem);
                            return carrito;
                        }
                    }
                }

            }
        } else {
            // Crear un nuevo carrito para el usuario
            Carrito nuevoCarrito = new Carrito();
            nuevoCarrito.setUsuario(new Usuario());
            nuevoCarrito.getUsuario().setId(producto.getIdUsuario());
            nuevoCarrito.setEstado(Estados.ACTIVO);
            carritoRepository.save(nuevoCarrito);

            // Agregar el primer item al carrito
            ItemsCarrito primerItem = new ItemsCarrito();
            primerItem.setCantidad(1);
            primerItem.setCarrito(nuevoCarrito);
            Productos productoNuevo = new Productos();
            productoNuevo.setId(producto.getId());
            primerItem.setProducto(productoNuevo);
            primerItem.setPrecioUnitario(Double.valueOf(producto.getPrecio()));
            itemsCarritoRepository.save(primerItem);

            return nuevoCarrito;
        }
                // En el caso de que no se encuentre el carrito, devolver un carrito vacío
                Carrito carritoVacio = new Carrito();
                return carritoVacio;
    }


    public Carrito borrarItemCarrito(Integer idItem){
        Optional<ItemsCarrito> itemOpt = itemsCarritoRepository.findById(idItem);
        if (itemOpt.isPresent()) {
            ItemsCarrito item = itemOpt.get();
            Carrito carrito = item.getCarrito();
            itemsCarritoRepository.delete(item);
            return carrito;
        } else {
            return null;
        }
    }
}
