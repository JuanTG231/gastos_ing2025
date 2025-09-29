package com.gip.gastos_ingresos.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "recurrencias")
public class Recurrencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con usuario
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // Relación con categoría
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @NotBlank(message = "El tipo debe ser 'ingreso' o 'gasto'")
    @Column(nullable = false)
    private String tipo;

    @NotNull(message = "El monto no puede ser nulo")
    @Column(nullable = false)
    private BigDecimal monto;

    @NotBlank(message = "La moneda es obligatoria (MXN, USD, USDT)")
    @Column(nullable = false, length = 10)
    private String moneda; // MXN, USD, USDT

    private String descripcion;

    @NotBlank(message = "La frecuencia es obligatoria (DIARIO, SEMANAL, MENSUAL, ANUAL)")
    @Column(nullable = false)
    private String frecuencia;

    @NotNull(message = "La fecha de inicio es obligatoria")
    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin; // Puede ser null si es indefinida

    // Constructor vacío requerido por JPA
    public Recurrencia() {}

    // Constructor con parámetros
    public Recurrencia(Usuario usuario, Categoria categoria, String tipo, BigDecimal monto,
                       String moneda, String descripcion, String frecuencia,
                       LocalDate fechaInicio, LocalDate fechaFin) {
        this.usuario = usuario;
        this.categoria = categoria;
        this.tipo = tipo;
        this.monto = monto;
        this.moneda = moneda;
        this.descripcion = descripcion;
        this.frecuencia = frecuencia;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }
}


