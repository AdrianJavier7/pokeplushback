package com.example.pokeplushback.Controladores;

import com.example.pokeplushback.Entidades.Usuario;
import com.example.pokeplushback.Servicios.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    @Autowired
    private EmailService emailService;

    @PostMapping("/enviarEmail")
    public ResponseEntity<String> registerUser(@RequestBody Usuario user) {
        emailService.enviarEmail(user.getEmail(), "¡Bienvenido/a a PokePush! 🧸⚡",
                "¡Nos alegra muchísimo tenerte en nuestra comunidad de peluches Pokémon! 🎉 PokePush es el lugar ideal para descubrir, compartir y disfrutar los peluches más adorables, conectar con otros entrenadores y llenar tu colección con ternura. 💖\n" +
                        "\n" +
                        "Aquí puedes:\n" +
                        "🧸 Explorar miles de peluches compartidos por otros usuarios.\n" +
                        "📸 Publicar tus propios peluches y mostrar tu colección con orgullo.\n" +
                        "❤️ Guardar tus favoritos y organizarlos fácilmente.\n" +
                        "💬 Interactuar con otros fans dejando comentarios y valoraciones.\n" +
                        "\n" +
                        "Para comenzar, te recomendamos:\n" +
                        "🔹 Completar tu perfil para que la comunidad te conozca mejor.\n" +
                        "\n" +
                        "Si necesitas ayuda, estamos aquí para ti. No dudes en visitar nuestra sección de ayuda o escribirnos.\n" +
                        "\n" +
                        "¡Esperamos ver tu increíble colección pronto! 🪶🧸✨\n" +
                        "\n" +
                        "Saludos,\n" +
                        "El equipo de PokePush ⚡🧸💫");
        return ResponseEntity.ok("Usuario registrado y correo enviado");
    }
}
