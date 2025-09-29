package com.gip.gastos_ingresos.service;
import com.gip.gastos_ingresos.dto.RecurrenciaUpdateDTO;
import com.gip.gastos_ingresos.entity.Recurrencia;
import com.gip.gastos_ingresos.entity.Usuario;
import com.gip.gastos_ingresos.entity.Categoria;
import com.gip.gastos_ingresos.repository.RecurrenciaRepository;
import com.gip.gastos_ingresos.repository.MovimientoRepository;

import com.gip.gastos_ingresos.service.CategoriaService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RecurrenciaService {

    private final RecurrenciaRepository recurrenciaRepository;
    private final MovimientoRepository movimientoRepository; // 👈 lo agregamos
    private final CategoriaService categoriaService; // 👈 nuevo


    public RecurrenciaService(RecurrenciaRepository recurrenciaRepository, MovimientoRepository movimientoRepository, CategoriaService categoriaService) {
        this.recurrenciaRepository = recurrenciaRepository;
        this.movimientoRepository = movimientoRepository; // 👈 inicializamos
        this.categoriaService = categoriaService; // 👈 inicializamos


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

        // ✅ Validar categoría pertenece al usuario
        Categoria cat = categoriaService.buscarPorIdYUsuario(categoria.getId(), usuario)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada o no pertenece al usuario"));

        recurrencia.setUsuario(usuario);
        recurrencia.setCategoria(cat);

        return recurrenciaRepository.save(recurrencia);
    }

    // Listar recurrencias de un usuario
    public List<Recurrencia> listarPorUsuario(Usuario usuario) {
        return recurrenciaRepository.findByUsuario(usuario);
    }

    // Listar recurrencias activas
    public List<Recurrencia> listarRecurrenciasActivas(Usuario usuario) {
        List<Recurrencia> activas = new ArrayList<>();
        activas.addAll(recurrenciaRepository.findByUsuarioAndFechaFinIsNull(usuario));
        activas.addAll(recurrenciaRepository.findByUsuarioAndFechaFinAfter(usuario, LocalDate.now()));
        return activas;
    }

    // Buscar recurrencia por ID
    public Optional<Recurrencia> buscarPorIdYUsuario(Long id, Usuario usuario) {
        return recurrenciaRepository.findByIdAndUsuario(id, usuario);
    }

    //editar Recurrencia
    public Recurrencia editarRecurrencia(Long id, RecurrenciaUpdateDTO detalles, Usuario usuario) {
        // Buscar la recurrencia del usuario
        Recurrencia existente = recurrenciaRepository.findByIdAndUsuario(id, usuario)
                .orElseThrow(() -> new RuntimeException("Recurrencia no encontrada o no pertenece al usuario"));

        // Actualizar campos solo si no vienen nulos
        if (detalles.getMonto() != null) existente.setMonto(detalles.getMonto());
        if (detalles.getDescripcion() != null) existente.setDescripcion(detalles.getDescripcion());
        if (detalles.getFrecuencia() != null) existente.setFrecuencia(detalles.getFrecuencia());
        if (detalles.getFechaInicio() != null) existente.setFechaInicio(detalles.getFechaInicio());
        if (detalles.getFechaFin() != null) existente.setFechaFin(detalles.getFechaFin());

        // Validar fechas
        if (existente.getFechaFin() != null &&
                existente.getFechaFin().isBefore(existente.getFechaInicio())) {
            throw new RuntimeException("La fecha de fin no puede ser anterior a la fecha de inicio");
        }

        // Validar y actualizar categoría si se manda un nuevo id
        if (detalles.getCategoriaId() != null) {
            Categoria cat = categoriaService.buscarPorIdYUsuario(detalles.getCategoriaId(), usuario)
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada o no pertenece al usuario"));
            existente.setCategoria(cat);
        }

        return recurrenciaRepository.save(existente);
    }

    // Eliminar recurrencia
    public void eliminarRecurrencia(Long id, Usuario usuario) {
        Recurrencia recurrencia = recurrenciaRepository.findByIdAndUsuario(id, usuario)
                .orElseThrow(() -> new RuntimeException("Recurrencia no encontrada o no pertenece al usuario"));

        // 🔎 validar si tiene movimientos asociados
        if (movimientoRepository.existsByRecurrencia(recurrencia)) {
            throw new RuntimeException("No se puede eliminar la recurrencia porque tiene movimientos asociados");
        }

        recurrenciaRepository.delete(recurrencia);
    }
}
