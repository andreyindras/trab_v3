
package br.edu.unicesumar.api.dto;

import br.edu.unicesumar.api.entity.Departamento;
import br.edu.unicesumar.api.entity.Palestrante;
import java.time.LocalDateTime;
import java.util.List;

public class EventoResponseDTO {

    private Long id;
    private String nome;
    private String descricao;
    private LocalDateTime data;
    private Integer limiteParticipantes;
    private DepartamentoResponseDTO departamento;
    private List<PalestranteResponseDTO> palestrantes;
    private Integer numeroInscritos;
    private String statusVagas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public DepartamentoResponseDTO getDepartamento() {
        return departamento;
    }

    public void setDepartamento(DepartamentoResponseDTO departamento) {
        this.departamento = departamento;
    }

    public List<PalestranteResponseDTO> getPalestrantes() {
        return palestrantes;
    }

    public void setPalestrantes(List<PalestranteResponseDTO> palestrantes) {
        this.palestrantes = palestrantes;
    }

    public Integer getNumeroInscritos() {
        return numeroInscritos;
    }

    public void setNumeroInscritos(Integer numeroInscritos) {
        this.numeroInscritos = numeroInscritos;
    }

    public String getStatusVagas() {
        return statusVagas;
    }

    public void setStatusVagas(String statusVagas) {
        this.statusVagas = statusVagas;
    }
}


