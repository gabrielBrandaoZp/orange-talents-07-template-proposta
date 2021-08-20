package br.com.zupacademy.msproposta.associacartao.externo;

import com.fasterxml.jackson.annotation.JsonCreator;

public class BloqueioResponse {

    private String resultado;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public BloqueioResponse(String resultado) {
        this.resultado = resultado;
    }

    public String getResultado() {
        return resultado;
    }
}
