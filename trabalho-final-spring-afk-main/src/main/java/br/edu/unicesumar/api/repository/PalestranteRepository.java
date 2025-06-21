package br.edu.unicesumar.api.repository;

import br.edu.unicesumar.api.entity.Palestrante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PalestranteRepository extends JpaRepository<Palestrante, Long> {

    @Query("SELECT COUNT(p) > 0 FROM Palestrante p JOIN p.eventos e WHERE p.id = :palestranteId")
    boolean temEventosVinculados(@Param("palestranteId") Long palestranteId);
}