package br.edu.unicesumar.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AlunoUpdateDTO {

    @NotBlank(message = "O nome do aluno é obrigatório")
    @Size(max = 100, message = "O nome do aluno deve ter no máximo 100 caracteres")
    private String nome;

    @NotBlank(message = "A matrícula do aluno é obrigatória")
    @Size(max = 20, message = "A matrícula do aluno deve ter no máximo 20 caracteres")
    private String matricula;

    @NotBlank(message = "O curso do aluno é obrigatório")
    @Size(max = 100, message = "O curso do aluno deve ter no máximo 100 caracteres")
    private String curso;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }
}


