
package br.edu.unicesumar.api.dto;

import br.edu.unicesumar.api.enums.StatusInscricao;
import java.time.LocalDateTime;

public class InscricaoResponseDTO {

    private Long id;
    private EventoResponseDTO evento;
    private AlunoResponseDTO aluno;
    private LocalDateTime dataInscricao;
    private StatusInscricao status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EventoResponseDTO getEvento() {
        return evento;
    }

    public void setEvento(EventoResponseDTO evento) {
        this.evento = evento;
    }

    public AlunoResponseDTO getAluno() {
        return aluno;
    }

    public void setAluno(AlunoResponseDTO aluno) {
        this.aluno = aluno;
    }

    public LocalDateTime getDataInscricao() {
        return dataInscricao;
    }

    public void setDataInscricao(LocalDateTime dataInscricao) {
        this.dataInscricao = dataInscricao;
    }

    public StatusInscricao getStatus() {
        return status;
    }

    public void setStatus(StatusInscricao status) {
        this.status = status;
    }
}


