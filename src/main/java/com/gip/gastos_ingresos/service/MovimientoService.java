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

    // Metodo centralizado para conversión a MXN aqui vamos a conectar la api de tipo de cambio
    private BigDecimal convertirAMxn(BigDecimal monto, String moneda) {
        if ("MXN".equalsIgnoreCase(moneda)) {
            return monto;
        }
        // TODO: aquí podrías integrar una API real de tipo de cambio
        return monto; // por ahora lo dejamos igual
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

        movimiento.setMontoMxn(convertirAMxn(movimiento.getMonto(), movimiento.getMoneda()));


        return movimientoRepository.save(movimiento);
    }

    //editar movimiento
    public Movimiento editarMovimiento(Movimiento existente, Movimiento datosActualizados, Categoria categoria, Recurrencia recurrencia) {
        existente.setCategoria(categoria);
        existente.setRecurrencia(recurrencia);
        existente.setTipo(datosActualizados.getTipo());
        existente.setMonto(datosActualizados.getMonto());
        existente.setMoneda(datosActualizados.getMoneda());
        existente.setDescripcion(datosActualizados.getDescripcion());
        existente.setFecha(datosActualizados.getFecha());

        // Recalcular montoMxn
        existente.setMontoMxn(convertirAMxn(datosActualizados.getMonto(), datosActualizados.getMoneda()));

        return movimientoRepository.save(existente);
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
    public Optional<Movimiento> buscarPorIdDeUsuario(Long id, Usuario usuario) {
        return movimientoRepository.findByIdAndUsuario(id, usuario);
    }

    // Eliminar movimiento
    public void eliminarMovimiento(Long id, Usuario usuario) {
        Movimiento mov = movimientoRepository.findByIdAndUsuario(id, usuario)
                .orElseThrow(() -> new RuntimeException("Movimiento no encontrado o no pertenece al usuario"));
        movimientoRepository.delete(mov);
    }
}
