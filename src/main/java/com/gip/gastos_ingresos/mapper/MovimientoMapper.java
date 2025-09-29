package com.gip.gastos_ingresos.mapper;

import com.gip.gastos_ingresos.dto.MovimientoResponseDTO;
import com.gip.gastos_ingresos.entity.Movimiento;
import org.springframework.stereotype.Component;

@Component
public class MovimientoMapper {
    public MovimientoResponseDTO toDto(Movimiento m) {
        MovimientoResponseDTO dto = new MovimientoResponseDTO();
        dto.setId(m.getId());
        dto.setCategoriaId(m.getCategoria() != null ? m.getCategoria().getId() : null);
        dto.setRecurrenciaId(m.getRecurrencia() != null ? m.getRecurrencia().getId() : null);
        dto.setTipo(m.getTipo());
        dto.setMonto(m.getMonto());
        dto.setMoneda(m.getMoneda());
        dto.setDescripcion(m.getDescripcion());
        dto.setFecha(m.getFecha());
        dto.setMontoMxn(m.getMontoMxn());
        return dto;
    }
}
