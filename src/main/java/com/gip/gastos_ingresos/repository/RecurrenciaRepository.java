package com.gip.gastos_ingresos.repository;
import com.gip.gastos_ingresos.entity.Recurrencia;
import com.gip.gastos_ingresos.entity.Usuario;
import com.gip.gastos_ingresos.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecurrenciaRepository extends JpaRepository<Recurrencia, Long> {

    // Listar recurrencias de un usuario
    List<Recurrencia> findByUsuario(Usuario usuario);

    // Listar recurrencias por usuario y tipo (ingreso/gasto)
    List<Recurrencia> findByUsuarioAndTipo(Usuario usuario, String tipo);

    // Listar recurrencias por usuario y categoría
    List<Recurrencia> findByUsuarioAndCategoria(Usuario usuario, Categoria categoria);

    // Listar recurrencias activas (sin fecha_fin o con fecha_fin posterior a hoy)
    List<Recurrencia> findByUsuarioAndFechaFinIsNullOrFechaFinAfter(Usuario usuario, java.time.LocalDate fecha);
}