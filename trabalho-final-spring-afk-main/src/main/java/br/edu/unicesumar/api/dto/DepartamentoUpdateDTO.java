
package br.edu.unicesumar.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class DepartamentoUpdateDTO {

    @NotBlank(message = "O nome do departamento é obrigatório")
    @Size(max = 100, message = "O nome do departamento deve ter no máximo 100 caracteres")
    private String nome;

    @NotBlank(message = "A sigla do departamento é obrigatória")
    @Size(max = 10, message = "A sigla do departamento deve ter no máximo 10 caracteres")
    private String sigla;

    @NotBlank(message = "O responsável pelo departamento é obrigatório")
    @Size(max = 100, message = "O responsável pelo departamento deve ter no máximo 100 caracteres")
    private String responsavel;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }
}


