package br.com.zupacademy.msproposta.novaproposta.externo;

public class SolicitacaoAnaliseResponse {

    private String documento;
    private String nome;
    private StatusSolicitacao resultadoSolicitacao;
    private String idProposta;

    public SolicitacaoAnaliseResponse(String documento, String nome, StatusSolicitacao resultadoSolicitacao, String idProposta) {
        this.documento = documento;
        this.nome = nome;
        this.resultadoSolicitacao = resultadoSolicitacao;
        this.idProposta = idProposta;
    }

    public StatusSolicitacao getResultadoSolicitacao() {
        return resultadoSolicitacao;
    }
}
