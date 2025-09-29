package com.gip.gastos_ingresos.config;
import com.gip.gastos_ingresos.security.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // 🔹 Permitir login
                        .requestMatchers("/auth/login").permitAll()
                        // 🔹 (opcional) permitir registrar usuarios nuevos
                        .requestMatchers(HttpMethod.POST, "/usuarios").permitAll()
                        // 👇 permitir que cualquier usuario autenticado consulte su propio perfil
                        .requestMatchers(HttpMethod.GET, "/usuarios/me").authenticated()
                        // 🔹 Solo ADMIN puede listar usuarios
                        .requestMatchers(HttpMethod.GET, "/usuarios/**").hasRole("ADMIN")
                        // 🔹 Todas las demás rutas requieren estar autenticado
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
