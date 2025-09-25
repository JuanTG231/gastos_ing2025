package com.gip.gastos_ingresos;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String hello() {
        return "¡Servidor funcionando en localhost:8080 🚀!";
    }
}