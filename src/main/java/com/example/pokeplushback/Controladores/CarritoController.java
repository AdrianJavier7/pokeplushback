package com.example.pokeplushback.Controladores;

import com.example.pokeplushback.Dto.CarritoDTO;
import com.example.pokeplushback.Dto.ProductosDTO;
import com.example.pokeplushback.Dto.ItemDTO;
import com.example.pokeplushback.Entidades.Carrito;
import com.example.pokeplushback.Entidades.Usuario;
import com.example.pokeplushback.Security.JWTService;
import com.example.pokeplushback.Servicios.CarritoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carrito")
@AllArgsConstructor
public class CarritoController {

    private CarritoService carritoService;
    private JWTService jwtService;

    @GetMapping("/obtener")
    public CarritoDTO obtenerCarrito(@RequestHeader ("Authorization") String token) {
        Usuario perfilUsuario = jwtService.extraerPerfilToken(token);

        return carritoService.getCarritoUsuario(perfilUsuario);
    }

    @PostMapping("/anyadir")
    public CarritoDTO anyadirAlCarrito(@RequestBody ProductosDTO producto) {
        return carritoService.anyadirAlCarrito(producto);
    }

    @PostMapping("/quitar")
    public CarritoDTO   quitarDelCarrito(@RequestBody ItemDTO item) {
        return carritoService.QuitarCantidadItemCarrito(item);
    }

    @PostMapping("/eliminar/{id}")
    public CarritoDTO eliminarDelCarrito(@PathVariable Integer id) {
        return carritoService.borrarItemCarrito(id);
    }

}
