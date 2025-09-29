package com.gip.gastos_ingresos.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MovimientoResponseDTO {
    private Long id;
    private Long categoriaId;
    private Long recurrenciaId;
    private String tipo;
    private BigDecimal monto;
    private String moneda;
    private String descripcion;
    private LocalDate fecha;
    private BigDecimal montoMxn;

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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

    public BigDecimal getMontoMxn() { return montoMxn; }
    public void setMontoMxn(BigDecimal montoMxn) { this.montoMxn = montoMxn; }
}
