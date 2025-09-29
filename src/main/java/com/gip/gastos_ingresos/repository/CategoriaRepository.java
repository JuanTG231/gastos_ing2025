package com.gip.gastos_ingresos.repository;

import com.gip.gastos_ingresos.entity.Categoria;
import com.gip.gastos_ingresos.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    // Buscar categorías por usuario
    List<Categoria> findByUsuario(Usuario usuario);

    // Buscar categorías por tipo (ingreso o gasto) de un usuario
    List<Categoria> findByUsuarioAndTipo(Usuario usuario, String tipo);

    // Verificar si ya existe una categoría con el mismo nombre para un usuario
    boolean existsByUsuarioAndNombre(Usuario usuario, String nombre);

    // Buscar una categoría validando que pertenezca al usuario
    Optional<Categoria> findByIdAndUsuario(Long id, Usuario usuario);
}
