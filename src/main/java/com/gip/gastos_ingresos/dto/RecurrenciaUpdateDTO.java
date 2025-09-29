package com.gip.gastos_ingresos.dto;
import java.math.BigDecimal;
import java.time.LocalDate;
public class RecurrenciaUpdateDTO {
    private BigDecimal monto;
    private String descripcion;
    private String frecuencia;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Long categoriaId; // si quieres permitir mover la recurrencia a otra categoría

    // Getters y setters
    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getFrecuencia() { return frecuencia; }
    public void setFrecuencia(String frecuencia) { this.frecuencia = frecuencia; }

    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }

    public Long getCategoriaId() { return categoriaId; }
    public void setCategoriaId(Long categoriaId) { this.categoriaId = categoriaId; }
}
