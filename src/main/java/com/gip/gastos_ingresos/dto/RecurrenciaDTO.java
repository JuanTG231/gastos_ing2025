package com.gip.gastos_ingresos.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RecurrenciaDTO {
    private Long id;
    private String tipo;
    private BigDecimal monto;
    private String moneda;
    private String descripcion;
    private String frecuencia;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private CategoriaDTO categoria;

    public RecurrenciaDTO(Long id, String tipo, BigDecimal monto, String moneda,
                          String descripcion, String frecuencia,
                          LocalDate fechaInicio, LocalDate fechaFin,
                          CategoriaDTO categoria) {
        this.id = id;
        this.tipo = tipo;
        this.monto = monto;
        this.moneda = moneda;
        this.descripcion = descripcion;
        this.frecuencia = frecuencia;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.categoria = categoria;
    }

    public Long getId() { return id; }
    public String getTipo() { return tipo; }
    public BigDecimal getMonto() { return monto; }
    public String getMoneda() { return moneda; }
    public String getDescripcion() { return descripcion; }
    public String getFrecuencia() { return frecuencia; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public LocalDate getFechaFin() { return fechaFin; }
    public CategoriaDTO getCategoria() { return categoria; }
}
