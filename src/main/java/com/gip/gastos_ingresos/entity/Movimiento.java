package com.gip.gastos_ingresos.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "movimientos")
public class Movimiento {
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
    private String moneda;

    private String descripcion;

    @NotNull(message = "La fecha es obligatoria")
    @Column(nullable = false)
    private LocalDate fecha;

    // Relación opcional con recurrencia
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recurrencia_id")
    private Recurrencia recurrencia; // Puede ser null si es movimiento único

    // Equivalente en MXN (calculado al guardar)
    @Column(name = "monto_mxn")
    private BigDecimal montoMxn;

    // Constructor vacío requerido por JPA
    public Movimiento() {}

    // Constructor con parámetros
    public Movimiento(Usuario usuario, Categoria categoria, String tipo, BigDecimal monto,
                      String moneda, String descripcion, LocalDate fecha,
                      Recurrencia recurrencia, BigDecimal montoMxn) {
        this.usuario = usuario;
        this.categoria = categoria;
        this.tipo = tipo;
        this.monto = monto;
        this.moneda = moneda;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.recurrencia = recurrencia;
        this.montoMxn = montoMxn;
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

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Recurrencia getRecurrencia() {
        return recurrencia;
    }

    public void setRecurrencia(Recurrencia recurrencia) {
        this.recurrencia = recurrencia;
    }

    public BigDecimal getMontoMxn() {
        return montoMxn;
    }

    public void setMontoMxn(BigDecimal montoMxn) {
        this.montoMxn = montoMxn;
    }
}
