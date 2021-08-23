package br.com.zupacademy.msproposta.associacartao.externo;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class AvisoViagemRequestClient {

    @NotBlank
    private String destino;

    @NotNull
    @Future
    private LocalDate validoAte;

    public AvisoViagemRequestClient(String destino, LocalDate validoAte) {
        this.destino = destino;
        this.validoAte = validoAte;
    }

    public String getDestino() {
        return destino;
    }

    public LocalDate getValidoAte() {
        return validoAte;
    }
}
