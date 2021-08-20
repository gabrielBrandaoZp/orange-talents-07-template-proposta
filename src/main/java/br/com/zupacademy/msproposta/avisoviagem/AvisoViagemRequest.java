package br.com.zupacademy.msproposta.avisoviagem;

import br.com.zupacademy.msproposta.associacartao.Cartao;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class AvisoViagemRequest {

    @NotBlank
    private String destino;

    @NotNull
    @Future
    private LocalDate terminaEm;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public AvisoViagemRequest(String destino, @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING) LocalDate terminaEm) {
        this.destino = destino;
        this.terminaEm = terminaEm;
    }

    public AvisoViagem paraAvisoViagem(String ip, String userAgent, Cartao cartao) {
        return new AvisoViagem(destino, terminaEm, ip, userAgent, cartao);
    }
}
