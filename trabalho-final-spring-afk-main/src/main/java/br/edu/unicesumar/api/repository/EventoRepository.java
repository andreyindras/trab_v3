package br.edu.unicesumar.api.repository;

import br.edu.unicesumar.api.entity.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {

    @Query("SELECT e FROM Evento e WHERE e.departamento.id = :departamentoId")
    List<Evento> findByDepartamentoId(@Param("departamentoId") Long departamentoId);

    @Query("SELECT e FROM Evento e WHERE e.data BETWEEN :inicio AND :fim")
    List<Evento> findEventosNoIntervalo(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);
}