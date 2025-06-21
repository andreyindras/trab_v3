package br.edu.unicesumar.api.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

public class EventoUpdateDTO {

    @NotBlank(message = "O nome do evento é obrigatório")
    private String nome;

    @NotBlank(message = "A descrição do evento é obrigatória")
    private String descricao;

    @NotNull(message = "A data do evento é obrigatória")
    @FutureOrPresent(message = "A data do evento não pode ser no passado")
    private LocalDateTime data;

    @NotNull(message = "O limite de participantes é obrigatório")
    @Min(value = 1, message = "O limite de participantes deve ser no mínimo 1")
    private Integer limiteParticipantes;

    @NotNull(message = "O departamento é obrigatório")
    private Long idDepartamento;

    @NotNull(message = "É necessário informar pelo menos um palestrante")
    private List<Long> idsPalestrantes;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public Integer getLimiteParticipantes() {
        return limiteParticipantes;
    }

    public void setLimiteParticipantes(Integer limiteParticipantes) {
        this.limiteParticipantes = limiteParticipantes;
    }

    public Long getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(Long idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public List<Long> getIdsPalestrantes() {
        return idsPalestrantes;
    }

    public void setIdsPalestrantes(List<Long> idsPalestrantes) {
        this.idsPalestrantes = idsPalestrantes;
    }
}


