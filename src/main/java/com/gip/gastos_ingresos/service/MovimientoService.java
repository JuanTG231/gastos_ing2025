package com.gip.gastos_ingresos.service;
import com.gip.gastos_ingresos.entity.Movimiento;
import com.gip.gastos_ingresos.entity.Usuario;
import com.gip.gastos_ingresos.entity.Categoria;
import com.gip.gastos_ingresos.entity.Recurrencia;
import com.gip.gastos_ingresos.repository.MovimientoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MovimientoService {
    private final MovimientoRepository movimientoRepository;

    public MovimientoService(MovimientoRepository movimientoRepository) {
        this.movimientoRepository = movimientoRepository;
    }

    // Crear un movimiento
    public Movimiento crearMovimiento(Movimiento movimiento, Usuario usuario, Categoria categoria, Recurrencia recurrencia) {
        // Validar tipo
        if (!movimiento.getTipo().equalsIgnoreCase("ingreso") &&
                !movimiento.getTipo().equalsIgnoreCase("gasto")) {
            throw new RuntimeException("El tipo debe ser 'ingreso' o 'gasto'");
        }

        // Validar moneda
        if (!(movimiento.getMoneda().equalsIgnoreCase("MXN") ||
                movimiento.getMoneda().equalsIgnoreCase("USD") ||
                movimiento.getMoneda().equalsIgnoreCase("USDT"))) {
            throw new RuntimeException("La moneda debe ser MXN, USD o USDT");
        }

        // Asignar usuario, categoría y recurrencia
        movimiento.setUsuario(usuario);
        movimiento.setCategoria(categoria);
        movimiento.setRecurrencia(recurrencia);

        // Si no viene fecha, asignamos la actual
        if (movimiento.getFecha() == null) {
            movimiento.setFecha(LocalDate.now());
        }

        // Calcular monto en MXN (en el futuro aquí conectamos con API de tipo de cambio)
        if (movimiento.getMoneda().equalsIgnoreCase("MXN")) {
            movimiento.setMontoMxn(movimiento.getMonto());
        } else {
            // Por ahora dejamos el equivalente igual, más adelante integramos API real
            movimiento.setMontoMxn(movimiento.getMonto());
        }

        return movimientoRepository.save(movimiento);
    }

    // Listar todos los movimientos de un usuario
    public List<Movimiento> listarPorUsuario(Usuario usuario) {
        return movimientoRepository.findByUsuario(usuario);
    }

    // Listar movimientos de un usuario por tipo
    public List<Movimiento> listarPorUsuarioYTipo(Usuario usuario, String tipo) {
        return movimientoRepository.findByUsuarioAndTipo(usuario, tipo);
    }

    // Listar movimientos por rango de fechas
    public List<Movimiento> listarPorUsuarioYFechas(Usuario usuario, LocalDate inicio, LocalDate fin) {
        return movimientoRepository.findByUsuarioAndFechaBetween(usuario, inicio, fin);
    }

    // Buscar movimiento por ID
    public Optional<Movimiento> buscarPorId(Long id) {
        return movimientoRepository.findById(id);
    }

    // Eliminar movimiento
    public void eliminarMovimiento(Long id) {
        movimientoRepository.deleteById(id);
    }
}
