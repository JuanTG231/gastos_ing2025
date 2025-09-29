package com.gip.gastos_ingresos.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de la categoría no puede estar vacío")
    @Column(nullable = false)
    private String nombre;

    @NotBlank(message = "El tipo debe ser 'ingreso' o 'gasto'")
    @Column(nullable = false)
    private String tipo; // ingreso / gasto

    // Relación con Usuario (muchas categorías pueden pertenecer a un usuario)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // Constructor vacío requerido por JPA
    public Categoria() {}

    // Constructor con parámetros
    public Categoria(String nombre, String tipo, Usuario usuario) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.usuario = usuario;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
