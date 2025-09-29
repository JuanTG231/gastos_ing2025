package com.gip.gastos_ingresos.dto;

public class LoginResponse {
    private String token;
    private String tipo = "Bearer";
    private String rol;

    public LoginResponse(String token, String rol) {
        this.token = token;
        this.rol = rol;
    }

    // Getters
    public String getToken() {
        return token;
    }

    public String getTipo() {
        return tipo;
    }

    public String getRol() {
        return rol;
    }
}
