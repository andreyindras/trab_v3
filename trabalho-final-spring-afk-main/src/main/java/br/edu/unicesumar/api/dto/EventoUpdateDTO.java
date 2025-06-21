package br.edu.unicesumar.api.dto;

import java.time.LocalDateTime;
import java.util.List;

public class EventoUpdateDTO {


    private String nome;
    private String descricao;
    private LocalDateTime data;
    private Integer limiteParticipantes;
    private Long idDepartamento;
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


