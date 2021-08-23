package br.com.zupacademy.msproposta.associacartao.externo;

import com.fasterxml.jackson.annotation.JsonCreator;

public class AvisoViagemResponseClient {

    private String resultado;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public AvisoViagemResponseClient(String resultado) {
        this.resultado = resultado;
    }

    public String getResultado() {
        return resultado;
    }
}
