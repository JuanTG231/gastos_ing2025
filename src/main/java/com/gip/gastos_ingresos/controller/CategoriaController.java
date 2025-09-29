package com.gip.gastos_ingresos.controller;

import com.gip.gastos_ingresos.entity.Categoria;
import com.gip.gastos_ingresos.entity.Usuario;
import com.gip.gastos_ingresos.service.CategoriaService;
import com.gip.gastos_ingresos.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;
    private final UsuarioService usuarioService;

    public CategoriaController(CategoriaService categoriaService, UsuarioService usuarioService) {
        this.categoriaService = categoriaService;
        this.usuarioService = usuarioService;
    }

    // Helper para obtener usuario autenticado desde el token
    private Usuario getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        return usuarioService.buscarPorEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario autenticado no encontrado"));
    }

    // 1. Crear categoría
    @PostMapping
    public ResponseEntity<Categoria> crearCategoria(@Valid @RequestBody Categoria categoria) {
        Usuario usuario = getCurrentUser();
        Categoria nueva = categoriaService.crearCategoria(categoria, usuario);
        return ResponseEntity.ok(nueva);
    }

    // 2. Listar todas las categorías del usuario autenticado
    @GetMapping
    public ResponseEntity<List<Categoria>> listarCategorias() {
        Usuario usuario = getCurrentUser();
        return ResponseEntity.ok(categoriaService.listarCategoriasPorUsuario(usuario));
    }

    // 3. Listar categorías filtradas por tipo
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Categoria>> listarPorTipo(@PathVariable String tipo) {
        Usuario usuario = getCurrentUser();
        return ResponseEntity.ok(categoriaService.listarCategoriasPorUsuarioYTipo(usuario, tipo));
    }

    // 4. Obtener una categoría por ID validando dueño
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> obtenerPorId(@PathVariable Long id) {
        Usuario usuario = getCurrentUser();
        Categoria categoria = categoriaService.buscarPorIdYUsuario(id, usuario)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        return ResponseEntity.ok(categoria);
    }

    // 5. Editar categoría validando dueño
    @PutMapping("/{id}")
    public ResponseEntity<Categoria> actualizar(@PathVariable Long id,
                                                @Valid @RequestBody Categoria detalles) {
        Usuario usuario = getCurrentUser();
        Categoria actualizada = categoriaService.actualizarCategoria(id, detalles, usuario);
        return ResponseEntity.ok(actualizada);
    }

    // 6. Eliminar categoría validando dueño
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Usuario usuario = getCurrentUser();
        categoriaService.eliminarCategoria(id, usuario);
        return ResponseEntity.noContent().build();
    }
}