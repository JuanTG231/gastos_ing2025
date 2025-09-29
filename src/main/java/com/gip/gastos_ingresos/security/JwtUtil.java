package com.gip.gastos_ingresos.security;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {
    private final Key key;
    private final long expirationMs;

    public JwtUtil(
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.expiration-ms}") long expirationMs
    ) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.expirationMs = expirationMs;
    }

    // 🔹 Generar token con email y rol
    public String generarToken(String email, String rol) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .setSubject(email)
                .addClaims(Map.of("rol", rol))
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // 🔹 Validar token (arroja excepción si es inválido/expirado)
    public Jws<Claims> parse(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }

    // 🔹 Obtener email (subject) desde token
    public String getEmail(String token) {
        return parse(token).getBody().getSubject();
    }

    // 🔹 Obtener rol desde token
    public String getRol(String token) {
        return parse(token).getBody().get("rol", String.class);
    }
}
