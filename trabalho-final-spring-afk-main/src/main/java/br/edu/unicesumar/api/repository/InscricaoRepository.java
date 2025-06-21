package br.edu.unicesumar.api.repository;

import br.edu.unicesumar.api.entity.Inscricao;
import br.edu.unicesumar.api.enums.StatusInscricao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface InscricaoRepository extends JpaRepository<Inscricao, Long> {

    @Query("SELECT COUNT(i) FROM Inscricao i WHERE i.evento.id = :eventoId AND i.status = 'ATIVA'")
    Long countInscricoesAtivasByEventoId(@Param("eventoId") Long eventoId);

    @Query("SELECT i FROM Inscricao i WHERE i.aluno.id = :alunoId AND i.status = 'ATIVA'")
    List<Inscricao> findInscricoesAtivasByAlunoId(@Param("alunoId") Long alunoId);

    @Query("SELECT i FROM Inscricao i WHERE i.aluno.id = :alunoId AND i.evento.id = :eventoId AND i.status = 'ATIVA'")
    List<Inscricao> findInscricaoAtivaByAlunoAndEvento(@Param("alunoId") Long alunoId, @Param("eventoId") Long eventoId);

    @Query("SELECT i FROM Inscricao i WHERE i.aluno.id = :alunoId AND i.evento.data BETWEEN :inicio AND :fim AND i.status = 'ATIVA'")
    List<Inscricao> findInscricoesAtivasNoIntervalo(@Param("alunoId") Long alunoId, @Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    @Query("SELECT COUNT(i) FROM Inscricao i WHERE i.evento.departamento.id = :departamentoId AND i.status = 'ATIVA'")
    Long countInscricoesByDepartamentoId(@Param("departamentoId") Long departamentoId);
}