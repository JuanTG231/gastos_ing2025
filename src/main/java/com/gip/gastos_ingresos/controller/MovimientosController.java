package com.gip.gastos_ingresos.controller;

import com.gip.gastos_ingresos.dto.MovimientoCreateDTO;
import com.gip.gastos_ingresos.dto.MovimientoUpdateDTO;
import com.gip.gastos_ingresos.dto.MovimientoResponseDTO;
import com.gip.gastos_ingresos.entity.Movimiento;
import com.gip.gastos_ingresos.entity.Usuario;
import com.gip.gastos_ingresos.entity.Categoria;
import com.gip.gastos_ingresos.entity.Recurrencia;
import com.gip.gastos_ingresos.service.MovimientoService;
import com.gip.gastos_ingresos.repository.CategoriaRepository;
import com.gip.gastos_ingresos.repository.RecurrenciaRepository;
import com.gip.gastos_ingresos.security.AuthUtils;
import com.gip.gastos_ingresos.mapper.MovimientoMapper;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/movimientos")
public class MovimientosController {
    private final MovimientoService movimientoService;
    private final CategoriaRepository categoriaRepository;
    private final RecurrenciaRepository recurrenciaRepository;
    private final AuthUtils authUtils;
    private final MovimientoMapper mapper;

    public MovimientosController(MovimientoService movimientoService,
                                 CategoriaRepository categoriaRepository,
                                 RecurrenciaRepository recurrenciaRepository,
                                 AuthUtils authUtils,
                                 MovimientoMapper mapper) {
        this.movimientoService = movimientoService;
        this.categoriaRepository = categoriaRepository;
        this.recurrenciaRepository = recurrenciaRepository;
        this.authUtils = authUtils;
        this.mapper = mapper;
    }

    // 1. Registrar un movimiento
    @PostMapping
    public ResponseEntity<MovimientoResponseDTO> crear(@Valid @RequestBody MovimientoCreateDTO dto) {
        Usuario usuario = authUtils.getUsuarioAutenticado();

        Categoria categoria = categoriaRepository.findByIdAndUsuario(dto.getCategoriaId(), usuario)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada o no pertenece al usuario"));

        Recurrencia recurrencia = null;
        if (dto.getRecurrenciaId() != null) {
            recurrencia = recurrenciaRepository.findByIdAndUsuario(dto.getRecurrenciaId(), usuario)
                    .orElseThrow(() -> new RuntimeException("Recurrencia no encontrada o no pertenece al usuario"));
        }

        Movimiento movimiento = new Movimiento();
        movimiento.setTipo(dto.getTipo());
        movimiento.setMonto(dto.getMonto());
        movimiento.setMoneda(dto.getMoneda());
        movimiento.setDescripcion(dto.getDescripcion());
        movimiento.setFecha(dto.getFecha() != null ? dto.getFecha() : LocalDate.now());

        Movimiento guardado = movimientoService.crearMovimiento(movimiento, usuario, categoria, recurrencia);
        return ResponseEntity.ok(mapper.toDto(guardado));
    }

    // 2. Listar todos los movimientos de un usuario
    @GetMapping
    public List<MovimientoResponseDTO> listarTodos() {
        Usuario usuario = authUtils.getUsuarioAutenticado();
        return movimientoService.listarPorUsuario(usuario)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    // 3. Listar movimientos por tipo
    @GetMapping("/tipo/{tipo}")
    public List<MovimientoResponseDTO> listarPorTipo(@PathVariable String tipo) {
        Usuario usuario = authUtils.getUsuarioAutenticado();
        return movimientoService.listarPorUsuarioYTipo(usuario, tipo)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    // 4. Listar movimientos por rango de fechas
    @GetMapping("/fechas")
    public List<MovimientoResponseDTO> listarPorFechas(@RequestParam LocalDate inicio,
                                                       @RequestParam LocalDate fin) {
        Usuario usuario = authUtils.getUsuarioAutenticado();
        return movimientoService.listarPorUsuarioYFechas(usuario, inicio, fin)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    // 5. Obtener un movimiento por ID
    @GetMapping("/{id}")
    public ResponseEntity<MovimientoResponseDTO> obtenerPorId(@PathVariable Long id) {
        Usuario usuario = authUtils.getUsuarioAutenticado();
        Movimiento mov = movimientoService.buscarPorIdDeUsuario(id, usuario)
                .orElseThrow(() -> new RuntimeException("Movimiento no encontrado o no pertenece al usuario"));
        return ResponseEntity.ok(mapper.toDto(mov));
    }

    // 6. Editar un movimiento
    @PutMapping("/{id}")
    public ResponseEntity<MovimientoResponseDTO> editar(@PathVariable Long id,
                                                        @Valid @RequestBody MovimientoUpdateDTO dto) {
        Usuario usuario = authUtils.getUsuarioAutenticado();

        Movimiento existente = movimientoService.buscarPorIdDeUsuario(id, usuario)
                .orElseThrow(() -> new RuntimeException("Movimiento no encontrado o no pertenece al usuario"));

        Categoria categoria = null;
        if (dto.getCategoriaId() != null) {
            categoria = categoriaRepository.findByIdAndUsuario(dto.getCategoriaId(), usuario)
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada o no pertenece al usuario"));
        } else {
            categoria = existente.getCategoria();
        }

        Recurrencia recurrencia = null;
        if (dto.getRecurrenciaId() != null) {
            recurrencia = recurrenciaRepository.findByIdAndUsuario(dto.getRecurrenciaId(), usuario)
                    .orElseThrow(() -> new RuntimeException("Recurrencia no encontrada o no pertenece al usuario"));
        } else {
            recurrencia = existente.getRecurrencia();
        }

        Movimiento actualizado = new Movimiento();
        actualizado.setTipo(dto.getTipo() != null ? dto.getTipo() : existente.getTipo());
        actualizado.setMonto(dto.getMonto() != null ? dto.getMonto() : existente.getMonto());
        actualizado.setMoneda(dto.getMoneda() != null ? dto.getMoneda() : existente.getMoneda());
        actualizado.setDescripcion(dto.getDescripcion() != null ? dto.getDescripcion() : existente.getDescripcion());
        actualizado.setFecha(dto.getFecha() != null ? dto.getFecha() : existente.getFecha());

        Movimiento guardado = movimientoService.editarMovimiento(existente, actualizado, categoria, recurrencia);
        return ResponseEntity.ok(mapper.toDto(guardado));
    }

    // 7. Eliminar un movimiento
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Usuario usuario = authUtils.getUsuarioAutenticado();
        movimientoService.eliminarMovimiento(id, usuario);
        return ResponseEntity.noContent().build();
    }
}
