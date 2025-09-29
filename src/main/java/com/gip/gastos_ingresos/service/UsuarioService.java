package com.gip.gastos_ingresos.service;

import com.gip.gastos_ingresos.entity.Usuario;
import com.gip.gastos_ingresos.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // Inyección de dependencias vía constructor
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    // Crear usuario con validaciones
    // UsuarioService.crearUsuario(...)
    public Usuario crearUsuario(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }
        // Encriptar contraseña antes de guardarla
        usuario.setPasswordHash(passwordEncoder.encode(usuario.getPasswordHash()));
        // Asignar fecha de registro
        usuario.setFechaRegistro(LocalDateTime.now());

        // ✅ Forzar siempre USER en registro público
        usuario.setRol("USER");

        return usuarioRepository.save(usuario);
    }

    // Obtener todos los usuarios
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    // Buscar usuario por ID
    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    // Buscar usuario por email
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    // 🔹 Actualizar usuario existente
    public Usuario actualizarUsuario(Long id, Usuario datosActualizados) {
        Usuario usuarioDb = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (datosActualizados.getNombre() != null) {
            usuarioDb.setNombre(datosActualizados.getNombre());
        }
        if (datosActualizados.getRol() != null) {
            usuarioDb.setRol(datosActualizados.getRol());
        }
        if (datosActualizados.getPasswordHash() != null && !datosActualizados.getPasswordHash().isBlank()) {
            usuarioDb.setPasswordHash(passwordEncoder.encode(datosActualizados.getPasswordHash()));
        }

        // No se cambia el email ni la fecha de registro
        return usuarioRepository.save(usuarioDb);
    }

    // Eliminar usuario
    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
}
