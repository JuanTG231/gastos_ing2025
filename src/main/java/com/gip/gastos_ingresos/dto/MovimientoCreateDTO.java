package com.gip.gastos_ingresos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public class MovimientoCreateDTO {

    @NotNull(message = "La categoría es obligatoria")
    private Long categoriaId;

    private Long recurrenciaId; // opcional

    @NotBlank(message = "El tipo es obligatorio (ingreso/gasto)")
    private String tipo;

    @NotNull(message = "El monto no puede ser nulo")
    private BigDecimal monto;

    @NotBlank(message = "La moneda es obligatoria (MXN, USD, USDT)")
    private String moneda;

    private String descripcion;
    private LocalDate fecha;

    // Getters y setters
    public Long getCategoriaId() { return categoriaId; }
    public void setCategoriaId(Long categoriaId) { this.categoriaId = categoriaId; }

    public Long getRecurrenciaId() { return recurrenciaId; }
    public void setRecurrenciaId(Long recurrenciaId) { this.recurrenciaId = recurrenciaId; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }

    public String getMoneda() { return moneda; }
    public void setMoneda(String moneda) { this.moneda = moneda; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
}
