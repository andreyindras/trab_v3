package br.edu.unicesumar.api.dto;

import jakarta.validation.constraints.NotNull;

public class InscricaoRequestDTO {

    @NotNull(message = "O ID do aluno é obrigatório")
    private Long idAluno;

    public Long getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(Long idAluno) {
        this.idAluno = idAluno;
    }
}


