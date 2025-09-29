package com.gip.gastos_ingresos.service;

import com.gip.gastos_ingresos.entity.Categoria;
import com.gip.gastos_ingresos.entity.Usuario;
import com.gip.gastos_ingresos.repository.CategoriaRepository;
import com.gip.gastos_ingresos.repository.MovimientoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final MovimientoRepository movimientoRepository;

    // Inyección de dependencias
    public CategoriaService(CategoriaRepository categoriaRepository, MovimientoRepository movimientoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.movimientoRepository = movimientoRepository;
    }

    // Crear categoría con validaciones
    public Categoria crearCategoria(Categoria categoria, Usuario usuario) {
        // Validar duplicados
        if (categoriaRepository.existsByUsuarioAndNombre(usuario, categoria.getNombre())) {
            throw new RuntimeException("Ya existe una categoría con ese nombre para este usuario");
        }

        // Validar tipo
        if (!categoria.getTipo().equalsIgnoreCase("ingreso") &&
                !categoria.getTipo().equalsIgnoreCase("gasto")) {
            throw new RuntimeException("El tipo debe ser 'ingreso' o 'gasto'");
        }

        categoria.setUsuario(usuario);
        return categoriaRepository.save(categoria);
    }

    // Listar todas las categorías de un usuario
    public List<Categoria> listarCategoriasPorUsuario(Usuario usuario) {
        return categoriaRepository.findByUsuario(usuario);
    }

    // Listar categorías por tipo de un usuario
    public List<Categoria> listarCategoriasPorUsuarioYTipo(Usuario usuario, String tipo) {
        return categoriaRepository.findByUsuarioAndTipo(usuario, tipo);
    }

    // Buscar categoría por ID validando dueño
    public Optional<Categoria> buscarPorIdYUsuario(Long id, Usuario usuario) {
        return categoriaRepository.findByIdAndUsuario(id, usuario);
    }

    // Actualizar categoría validando dueño
    public Categoria actualizarCategoria(Long id, Categoria detalles, Usuario usuario) {
        Categoria categoria = categoriaRepository.findByIdAndUsuario(id, usuario)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada o no pertenece al usuario"));

        // Validar duplicados solo si cambia el nombre
        if (!categoria.getNombre().equalsIgnoreCase(detalles.getNombre())
                && categoriaRepository.existsByUsuarioAndNombre(usuario, detalles.getNombre())) {
            throw new RuntimeException("Ya existe una categoría con ese nombre para este usuario");
        }

        // Validar tipo
        if (!detalles.getTipo().equalsIgnoreCase("ingreso") &&
                !detalles.getTipo().equalsIgnoreCase("gasto")) {
            throw new RuntimeException("El tipo debe ser 'ingreso' o 'gasto'");
        }

        categoria.setNombre(detalles.getNombre());
        categoria.setTipo(detalles.getTipo());

        return categoriaRepository.save(categoria);
    }

    // Eliminar categoría validando dueño y movimientos asociados
    public void eliminarCategoria(Long id, Usuario usuario) {
        Categoria categoria = categoriaRepository.findByIdAndUsuario(id, usuario)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada o no pertenece al usuario"));

        if (movimientoRepository.existsByCategoriaId(categoria.getId())) {
            throw new RuntimeException("No se puede eliminar la categoría porque tiene movimientos asociados");
        }

        categoriaRepository.delete(categoria);
    }
}
