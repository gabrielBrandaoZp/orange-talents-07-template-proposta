package br.com.zupacademy.msproposta.associacartao.externo;

import br.com.zupacademy.msproposta.associacartao.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public class SolicitacaoCartaoResponse {

    private String id;
    private String titular;
    private String idProposta;
    private BigDecimal limite;
    private LocalDateTime emitidoEm;
    private Set<AvisoResponse> avisos;
    private Set<CarteiraResponse> carteiras;
    private Set<ParcelaResponse> parcelas;
    private RenegociacaoResponse renegociacao;
    private VencimentoResponse vencimento;

    public SolicitacaoCartaoResponse(String id, String titular, String idProposta, BigDecimal limite,  Set<AvisoResponse> avisos,
                                     Set<CarteiraResponse> carteiras, Set<ParcelaResponse> parcelas, RenegociacaoResponse renegociacao, VencimentoResponse vencimento, LocalDateTime emitidoEm) {
        this.id = id;
        this.titular = titular;
        this.idProposta = idProposta;
        this.limite = limite;
        this.avisos = avisos;
        this.carteiras = carteiras;
        this.parcelas = parcelas;
        this.renegociacao = renegociacao;
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

    public Set<AvisoResponse> getAvisos() {
        return avisos;
    }

    public Set<CarteiraResponse> getCarteiras() {
        return carteiras;
    }

    public Set<ParcelaResponse> getParcelas() {
        return parcelas;
    }

    public RenegociacaoResponse getRenegociacao() {
        return renegociacao;
    }

    public VencimentoResponse getVencimento() {
        return vencimento;
    }
}
