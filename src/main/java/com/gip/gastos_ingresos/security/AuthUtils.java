package com.gip.gastos_ingresos.security;

import com.gip.gastos_ingresos.entity.Usuario;
import com.gip.gastos_ingresos.repository.UsuarioRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthUtils {
    private final UsuarioRepository usuarioRepository;

    public AuthUtils(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario getUsuarioAutenticado() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName(); // aquí viene el email del token
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
}
