package br.com.zupacademy.msproposta.associacartao;

import java.time.LocalDate;

public class AvisoResponse {

    private LocalDate validoAte;
    private String destino;

    @Deprecated
    public AvisoResponse() {
    }

    public AvisoResponse(LocalDate validoAte, String destino) {
        this.validoAte = validoAte;
        this.destino = destino;
    }

    public LocalDate getValidoAte() {
        return validoAte;
    }

    public String getDestino() {
        return destino;
    }
}
