package com.gip.gastos_ingresos.controller;

import com.gip.gastos_ingresos.dto.RecurrenciaDTO;
import com.gip.gastos_ingresos.dto.RecurrenciaUpdateDTO;
import com.gip.gastos_ingresos.entity.Recurrencia;
import com.gip.gastos_ingresos.entity.Usuario;
import com.gip.gastos_ingresos.mapper.RecurrenciaMapper;
import com.gip.gastos_ingresos.service.RecurrenciaService;
import com.gip.gastos_ingresos.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/recurrencias")
public class RecurrenciaController {

    private final RecurrenciaService recurrenciaService;
    private final UsuarioService usuarioService;

    public RecurrenciaController(RecurrenciaService recurrenciaService,
                                 UsuarioService usuarioService) {
        this.recurrenciaService = recurrenciaService;
        this.usuarioService = usuarioService;
    }

    // Helper para obtener usuario autenticado desde el token
    private Usuario getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        return usuarioService.buscarPorEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario autenticado no encontrado"));
    }

    // 1. Crear recurrencia
    @PostMapping
    public ResponseEntity<RecurrenciaDTO> crearRecurrencia(@Valid @RequestBody Recurrencia recurrencia) {
        Usuario usuario = getCurrentUser();
        Recurrencia nueva = recurrenciaService.crearRecurrencia(recurrencia, usuario, recurrencia.getCategoria());
        return ResponseEntity.ok(RecurrenciaMapper.toDTO(nueva));
    }

    // 2. Listar todas las recurrencias del usuario autenticado
    @GetMapping
    public ResponseEntity<List<RecurrenciaDTO>> listarRecurrencias() {
        Usuario usuario = getCurrentUser();
        List<RecurrenciaDTO> lista = recurrenciaService.listarPorUsuario(usuario)
                .stream()
                .map(RecurrenciaMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    // 3. Listar recurrencias activas
    @GetMapping("/activas")
    public ResponseEntity<List<RecurrenciaDTO>> listarRecurrenciasActivas() {
        Usuario usuario = getCurrentUser();
        List<RecurrenciaDTO> lista = recurrenciaService.listarRecurrenciasActivas(usuario)
                .stream()
                .map(RecurrenciaMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    // 4. Obtener recurrencia por ID
    @GetMapping("/{id}")
    public ResponseEntity<RecurrenciaDTO> obtenerPorId(@PathVariable Long id) {
        Usuario usuario = getCurrentUser();
        Recurrencia recurrencia = recurrenciaService.buscarPorIdYUsuario(id, usuario)
                .orElseThrow(() -> new RuntimeException("Recurrencia no encontrada"));
        return ResponseEntity.ok(RecurrenciaMapper.toDTO(recurrencia));
    }

    // 5. Editar recurrencia
    @PutMapping("/{id}")
    public ResponseEntity<RecurrenciaDTO> actualizar(@PathVariable Long id,
                                                     @RequestBody RecurrenciaUpdateDTO detalles) {
        Usuario usuario = getCurrentUser();
        Recurrencia actualizada = recurrenciaService.editarRecurrencia(id, detalles, usuario);
        return ResponseEntity.ok(RecurrenciaMapper.toDTO(actualizada));
    }

    // 6. Eliminar recurrencia
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Usuario usuario = getCurrentUser();
        recurrenciaService.eliminarRecurrencia(id, usuario);
        return ResponseEntity.noContent().build();
    }
}