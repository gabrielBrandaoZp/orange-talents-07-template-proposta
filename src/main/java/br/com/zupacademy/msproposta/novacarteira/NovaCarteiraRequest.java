package br.com.zupacademy.msproposta.novacarteira;

import br.com.zupacademy.msproposta.associacartao.Cartao;

public interface NovaCarteiraRequest {

    Carteira paraCarteira(Cartao cartao);
    String getEmailRequest();
    TipoCarteira getTipoCarteira();
}
