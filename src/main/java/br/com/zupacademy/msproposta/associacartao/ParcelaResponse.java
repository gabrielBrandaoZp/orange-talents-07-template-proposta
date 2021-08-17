package br.com.zupacademy.msproposta.associacartao;

import java.math.BigDecimal;

public class ParcelaResponse {

    private String id;
    private Integer quantidade;
    private BigDecimal valor;

    @Deprecated
    public ParcelaResponse(){}

    public ParcelaResponse(ParcelaResponse response) {
        this.id = id;
        this.quantidade = quantidade;
        this.valor = valor;
    }

    public String getId() {
        return id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public BigDecimal getValor() {
        return valor;
    }
}
