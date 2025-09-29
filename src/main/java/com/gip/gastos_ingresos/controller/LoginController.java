package com.gip.gastos_ingresos.controller;

import com.gip.gastos_ingresos.dto.LoginRequest;
import com.gip.gastos_ingresos.dto.LoginResponse;
import com.gip.gastos_ingresos.entity.Usuario;
import com.gip.gastos_ingresos.repository.UsuarioRepository;
import com.gip.gastos_ingresos.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class LoginController {
    private final UsuarioRepository usuarioRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public LoginController(UsuarioRepository usuarioRepository, JwtUtil jwtUtil) {
        this.usuarioRepository = usuarioRepository;
        this.jwtUtil = jwtUtil;
    }

    // 🔹 Endpoint de login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        Optional<Usuario> opt = usuarioRepository.findByEmail(req.getEmail());

        if (opt.isEmpty()) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }

        Usuario usuario = opt.get();

        // Validar contraseña encriptada
        if (!passwordEncoder.matches(req.getPassword(), usuario.getPasswordHash())) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }

        // Generar JWT con email y rol
        String token = jwtUtil.generarToken(usuario.getEmail(), usuario.getRol());

        // Devolver respuesta con token y rol
        return ResponseEntity.ok(new LoginResponse(token, usuario.getRol()));
    }

}
