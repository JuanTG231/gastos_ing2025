package com.gip.gastos_ingresos.service;
import com.gip.gastos_ingresos.entity.Recurrencia;
import com.gip.gastos_ingresos.entity.Usuario;
import com.gip.gastos_ingresos.entity.Categoria;
import com.gip.gastos_ingresos.repository.RecurrenciaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class RecurrenciaService {

    private final RecurrenciaRepository recurrenciaRepository;

    public RecurrenciaService(RecurrenciaRepository recurrenciaRepository) {
        this.recurrenciaRepository = recurrenciaRepository;
    }

    // Crear una nueva recurrencia
    public Recurrencia crearRecurrencia(Recurrencia recurrencia, Usuario usuario, Categoria categoria) {
        // Validar tipo
        if (!recurrencia.getTipo().equalsIgnoreCase("ingreso") &&
                !recurrencia.getTipo().equalsIgnoreCase("gasto")) {
            throw new RuntimeException("El tipo debe ser 'ingreso' o 'gasto'");
        }

        // Validar moneda
        if (!(recurrencia.getMoneda().equalsIgnoreCase("MXN") ||
                recurrencia.getMoneda().equalsIgnoreCase("USD") ||
                recurrencia.getMoneda().equalsIgnoreCase("USDT"))) {
            throw new RuntimeException("La moneda debe ser MXN, USD o USDT");
        }

        // Validar frecuencia
        if (!(recurrencia.getFrecuencia().equalsIgnoreCase("DIARIO") ||
                recurrencia.getFrecuencia().equalsIgnoreCase("SEMANAL") ||
                recurrencia.getFrecuencia().equalsIgnoreCase("MENSUAL") ||
                recurrencia.getFrecuencia().equalsIgnoreCase("ANUAL"))) {
            throw new RuntimeException("La frecuencia debe ser DIARIO, SEMANAL, MENSUAL o ANUAL");
        }

        // Validar fechas
        if (recurrencia.getFechaFin() != null &&
                recurrencia.getFechaFin().isBefore(recurrencia.getFechaInicio())) {
            throw new RuntimeException("La fecha de fin no puede ser anterior a la fecha de inicio");
        }

        recurrencia.setUsuario(usuario);
        recurrencia.setCategoria(categoria);

        return recurrenciaRepository.save(recurrencia);
    }

    // Listar recurrencias de un usuario
    public List<Recurrencia> listarPorUsuario(Usuario usuario) {
        return recurrenciaRepository.findByUsuario(usuario);
    }

    // Listar recurrencias activas
    public List<Recurrencia> listarRecurrenciasActivas(Usuario usuario) {
        return recurrenciaRepository.findByUsuarioAndFechaFinIsNullOrFechaFinAfter(usuario, LocalDate.now());
    }

    // Buscar recurrencia por ID
    public Optional<Recurrencia> buscarPorId(Long id) {
        return recurrenciaRepository.findById(id);
    }

    // Eliminar recurrencia
    public void eliminarRecurrencia(Long id) {
        recurrenciaRepository.deleteById(id);
    }
}
