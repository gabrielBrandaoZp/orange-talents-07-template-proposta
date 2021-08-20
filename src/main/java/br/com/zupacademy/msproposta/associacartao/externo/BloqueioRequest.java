package br.com.zupacademy.msproposta.associacartao.externo;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.NotBlank;

public class BloqueioRequest {

    @NotBlank
    private String sistemaResponsavel;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public BloqueioRequest(String sistemaResponsavel) {
        this.sistemaResponsavel = sistemaResponsavel;
    }

    public String getSistemaResponsavel() {
        return sistemaResponsavel;
    }
}
