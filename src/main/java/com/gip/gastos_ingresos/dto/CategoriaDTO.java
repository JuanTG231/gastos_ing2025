package com.gip.gastos_ingresos.dto;

public class CategoriaDTO {
    private Long id;
    private String nombre;
    private String tipo;

    public CategoriaDTO(Long id, String nombre, String tipo) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getTipo() { return tipo; }
}
