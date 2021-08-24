package br.com.zupacademy.msproposta.associacartao.externo;

import java.time.LocalDateTime;


public class VencimentoResponse {

    private String id;
    private Integer dia;
    private LocalDateTime dataDeCriacao;

    @Deprecated
    public VencimentoResponse(){}

    public VencimentoResponse(String id, Integer dia, LocalDateTime dataDeCriacao) {
        this.id = id;
        this.dia = dia;
        this.dataDeCriacao = dataDeCriacao;
    }

    public String getId() {
        return id;
    }

    public Integer getDia() {
        return dia;
    }

    public LocalDateTime getDataDeCriacao() {
        return dataDeCriacao;
    }
}
