package br.com.zupacademy.msproposta.associacartao.externo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class SolicitacaoCartaoResponse {

    private String id;
    private String titular;
    private String idProposta;
    private BigDecimal limite;
    private LocalDateTime emitidoEm;
    private VencimentoResponse vencimento;

    public SolicitacaoCartaoResponse(String id, String titular, String idProposta, BigDecimal limite, VencimentoResponse vencimento,
                                     LocalDateTime emitidoEm) {
        this.id = id;
        this.titular = titular;
        this.idProposta = idProposta;
        this.limite = limite;
        this.vencimento = vencimento;
        this.emitidoEm = emitidoEm;
    }

    public String getId() {
        return id;
    }

    public String getTitular() {
        return titular;
    }

    public String getIdProposta() {
        return idProposta;
    }

    public BigDecimal getLimite() {
        return limite;
    }

    public LocalDateTime getEmitidoEm() {
        return emitidoEm;
    }

    public VencimentoResponse getVencimento() {
        return vencimento;
    }
}
