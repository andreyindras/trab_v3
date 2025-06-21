package br.edu.unicesumar.api.repository;

import br.edu.unicesumar.api.entity.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    @Query("SELECT COUNT(a) > 0 FROM Aluno a WHERE a.matricula = :matricula AND a.id != :id")
    boolean existsMatriculaExcludingId(@Param("matricula") String matricula, @Param("id") Long id);
    
    @Query("SELECT COUNT(a) > 0 FROM Aluno a WHERE a.matricula = :matricula")
    boolean existsByMatricula(@Param("matricula") String matricula);
}
