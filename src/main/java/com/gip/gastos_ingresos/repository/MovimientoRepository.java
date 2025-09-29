package com.gip.gastos_ingresos.repository;
import com.gip.gastos_ingresos.entity.Movimiento;
import com.gip.gastos_ingresos.entity.Usuario;
import com.gip.gastos_ingresos.entity.Categoria;
import com.gip.gastos_ingresos.entity.Recurrencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

    // Verificar si existen movimientos ligados a una categoría
    boolean existsByCategoriaId(Long categoriaId);

    // Buscar todos los movimientos de un usuario
    List<Movimiento> findByUsuario(Usuario usuario);

    // Buscar movimientos de un usuario filtrados por tipo (ingreso o gasto)
    List<Movimiento> findByUsuarioAndTipo(Usuario usuario, String tipo);

    // Buscar movimientos de un usuario en una categoría específica
    List<Movimiento> findByUsuarioAndCategoria(Usuario usuario, Categoria categoria);

    // Buscar movimientos de un usuario en un rango de fechas
    List<Movimiento> findByUsuarioAndFechaBetween(Usuario usuario, LocalDate inicio, LocalDate fin);

    // Buscar movimientos asociados a una recurrencia
    List<Movimiento> findByRecurrencia(Recurrencia recurrencia);
}