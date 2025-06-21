
package br.edu.unicesumar.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PalestranteUpdateDTO {

    @NotBlank(message = "O nome do palestrante é obrigatório")
    @Size(max = 100, message = "O nome do palestrante deve ter no máximo 100 caracteres")
    private String nome;

    @NotBlank(message = "O mini currículo do palestrante é obrigatório")
    private String miniCurriculo;

    @NotBlank(message = "A instituição do palestrante é obrigatória")
    @Size(max = 100, message = "A instituição do palestrante deve ter no máximo 100 caracteres")
    private String instituicao;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMiniCurriculo() {
        return miniCurriculo;
    }

    public void setMiniCurriculo(String miniCurriculo) {
        this.miniCurriculo = miniCurriculo;
    }

    public String getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(String instituicao) {
        this.instituicao = instituicao;
    }
}


