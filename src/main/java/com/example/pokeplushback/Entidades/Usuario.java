package com.example.pokeplushback.Entidades;

import com.example.pokeplushback.Enums.Nivel;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "usuario", schema = "pokeplush" , catalog = "postgres")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellidos")
    private String apellidos;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "fecha_nac")
    private Date fecha_nacimiento;

    @Column(name = "fecha_reg")
    private Date fecha_registro;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "foto")
    private String foto;

    @Column(name = "password")
    private String password;

    @Column(name = "nivel")
    @Enumerated(EnumType.STRING)
    private Nivel nivel;

    @Column(name = "cod_verif")
    private String codigo_verificacion;

    @Column(name = "verificado")
    private Boolean verificado = false;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Direccion> direcciones;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Opiniones> opiniones;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Carrito> carritos;

    // El nombre de usuario será el email (UserDetails)
    @Override
    public String getUsername() {
        return this.email;
    }

    // Devuelve los niveles de usuario (usa el enum)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(this.nivel.name()));
    }

    // Indice si la cuenta no ha expirado
    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    //Indica si la cuenta no está bloqueada
    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    // Indica si las credenciales no han expirado (contraseña)
    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    // Indica si el usuario está habilitado
    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    // Metodo para verificar si el usuario ha sido verificado
    public boolean isVerificado() {
        return Boolean.TRUE.equals(this.verificado);
    }

    /* Relación con Categoria (Uno a Muchos) (Lado del 1)
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Categoria> categorias;
    */

    /* Relación con Categoria (Uno a Uno)
    @OneToOne(mappedBy = "usuario")
    private Categoria categoria;
    */

    /* Relación con Categoria (Muchos a Muchos)
    @ManyToMany(mappedBy = "usuarios")
    private List<Categoria> categorias;
    */
}
