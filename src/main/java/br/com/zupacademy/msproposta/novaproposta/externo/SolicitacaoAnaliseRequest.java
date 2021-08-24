package br.com.zupacademy.msproposta.novaproposta.externo;

import br.com.zupacademy.msproposta.novaproposta.Proposta;

/**
 * Os getters da classe foram gerados para que o objeto possa ser serializado para JSON e possa ser enviado como corpo
 * da requisição na chamada a api externa.
 */
public class SolicitacaoAnaliseRequest {

    private String documento;
    private String nome;
    private String idProposta;

    public SolicitacaoAnaliseRequest(Proposta proposta) {
        this.documento = proposta.getDocumento();
        this.nome = proposta.getNome();
        this.idProposta = proposta.getId().toString();
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public String getIdProposta() {
        return idProposta;
    }
}
