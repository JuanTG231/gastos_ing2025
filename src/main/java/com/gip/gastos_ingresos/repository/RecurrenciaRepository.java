package com.gip.gastos_ingresos.repository;
import com.gip.gastos_ingresos.entity.Recurrencia;
import com.gip.gastos_ingresos.entity.Usuario;
import com.gip.gastos_ingresos.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import java.util.List;

@Repository
public interface RecurrenciaRepository extends JpaRepository<Recurrencia, Long> {

    Optional<Recurrencia> findByIdAndUsuario(Long id, Usuario usuario);

    // Listar recurrencias de un usuario
    List<Recurrencia> findByUsuario(Usuario usuario);

    // Listar recurrencias por usuario y tipo (ingreso/gasto)
    List<Recurrencia> findByUsuarioAndTipo(Usuario usuario, String tipo);

    // Listar recurrencias por usuario y categoría
    List<Recurrencia> findByUsuarioAndCategoria(Usuario usuario, Categoria categoria);

    // Listar recurrencias activas (sin fecha_fin o con fecha_fin posterior a hoy)
    List<Recurrencia> findByUsuarioAndFechaFinIsNull(Usuario usuario);
    List<Recurrencia> findByUsuarioAndFechaFinAfter(Usuario usuario, LocalDate fecha);
}