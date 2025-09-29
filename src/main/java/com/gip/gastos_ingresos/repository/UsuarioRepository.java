package com.gip.gastos_ingresos.repository;

import com.gip.gastos_ingresos.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Buscar un usuario por email (útil para login)
    Optional<Usuario> findByEmail(String email);

    // Verificar si un email ya está registrado
    boolean existsByEmail(String email);
}