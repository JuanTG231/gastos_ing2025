package com.gip.gastos_ingresos.mapper;

import com.gip.gastos_ingresos.dto.CategoriaDTO;
import com.gip.gastos_ingresos.dto.RecurrenciaDTO;
import com.gip.gastos_ingresos.entity.Recurrencia;

public class RecurrenciaMapper {
    public static RecurrenciaDTO toDTO(Recurrencia r) {
        CategoriaDTO categoriaDTO = new CategoriaDTO(
                r.getCategoria().getId(),
                r.getCategoria().getNombre(),
                r.getCategoria().getTipo()
        );

        return new RecurrenciaDTO(
                r.getId(),
                r.getTipo(),
                r.getMonto(),
                r.getMoneda(),
                r.getDescripcion(),
                r.getFrecuencia(),
                r.getFechaInicio(),
                r.getFechaFin(),
                categoriaDTO
        );
    }
}
