package com.example.pokeplushback.Controladores;

import com.example.pokeplushback.Dto.ProductosDTO;
import com.example.pokeplushback.Dto.ItemDTO;
import com.example.pokeplushback.Entidades.Carrito;
import com.example.pokeplushback.Entidades.Usuario;
import com.example.pokeplushback.Servicios.CarritoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carrito")
@AllArgsConstructor
public class CarritoController {

    private CarritoService carritoService;

    @GetMapping("/obtener")
    public Carrito obtenerCarrito() {

        // usuario de prueba por ahora
        Usuario usuario = new Usuario();

        return carritoService.getCarritoUsuario(usuario);
    }

    @PostMapping("/anyadir")
    public Carrito añadirAlCarrito(@RequestBody ProductosDTO producto) {
        return carritoService.anyadirAlCarrito(producto);
    }

    @PostMapping("/quitar")
    public Carrito quitarDelCarrito(@RequestBody ItemDTO item) {
        return carritoService.QuitarCantidadItemCarrito(item);
    }

    @PostMapping("/eliminar/{id}")
    public Carrito eliminarDelCarrito(@PathVariable Integer item) {
        return carritoService.borrarItemCarrito(item);
    }

}
