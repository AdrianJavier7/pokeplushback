package com.example.pokeplushback.Controladores;

import com.example.pokeplushback.Dto.CarritoDTO;
import com.example.pokeplushback.Dto.ProductosDTO;
import com.example.pokeplushback.Dto.ItemDTO;
import com.example.pokeplushback.Entidades.Carrito;
import com.example.pokeplushback.Entidades.Usuario;
import com.example.pokeplushback.Security.JWTService;
import com.example.pokeplushback.Servicios.CarritoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/anyadir/{idProducto}")
    public CarritoDTO anyadirAlCarrito(@PathVariable Integer idProducto, @RequestHeader ("Authorization") String token) {
        Usuario perfilUsuario = jwtService.extraerPerfilToken(token);
        return carritoService.anyadirAlCarrito(idProducto, perfilUsuario);
    }

    @PostMapping("/procesando")
    public CarritoDTO procesarCarrito(@RequestHeader ("Authorization") String token) {
        Usuario perfilUsuario = jwtService.extraerPerfilToken(token);
        return carritoService.cambiarEstadoProcesando(perfilUsuario);
    }

    @GetMapping("/verPedidos")
    public List<CarritoDTO> verTodosLosPedidos() {
        return carritoService.obtenerTodosLosPedidos();
    }

    @GetMapping("/pedidos")
    public List<CarritoDTO> obtenerPedidosUsuario(@RequestHeader("Authorization") String token) {
        Usuario perfilUsuario = jwtService.extraerPerfilToken(token);
        return carritoService.obtenerCarritosPedidos(perfilUsuario);
    }

    @PostMapping("/quitar")
    public CarritoDTO   quitarDelCarrito(@RequestBody ItemDTO item) {
        return carritoService.QuitarCantidadItemCarrito(item);
    }

    @PostMapping("/eliminar/{id}")
    public CarritoDTO eliminarDelCarrito(@PathVariable Integer id) {
        return carritoService.borrarItemCarrito(id);
    }

    @PostMapping("/cancelar/{id}")
    public CarritoDTO cancelarPedido(@RequestHeader ("Authorization") String token, @PathVariable Integer id) {
        Usuario perfilUsuario = jwtService.extraerPerfilToken(token);
        return carritoService.cancelarPedido(perfilUsuario, id);
    }

    @DeleteMapping("/eliminarPedidos/{id}")
    public ResponseEntity<Void> eliminarPedido(@RequestHeader("Authorization") String token, @PathVariable Integer id) {
        Usuario perfilUsuario = jwtService.extraerPerfilToken(token);
        return carritoService.eliminarPedido(id, perfilUsuario);
    }

    @GetMapping("/obtener/{id}")
    public CarritoDTO obtenerCarritoPorId(@RequestHeader("Authorization") String token, @PathVariable Integer id) {
        Usuario perfilUsuario = jwtService.extraerPerfilToken(token);

        return carritoService.obtenerPedidoActual(perfilUsuario, id);
    }

}
