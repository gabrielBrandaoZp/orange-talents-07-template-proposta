package br.com.zupacademy.msproposta.novacarteira;

import br.com.zupacademy.msproposta.associacartao.Cartao;

public interface NovaCarteiraRequest {

    Carteira paraCarteira(Cartao cartao);
    String getEmailRequest();

    /**
     * Esse método deve ser usado pelas classes de requisição que implementam a interface acima.
     * Seu uso faz-se necessário para que o método de processar o cadastro de uma carteira fique o mais genérico possível.
     * @return TipoCarteira o tipo de carteira específica (Ex.: PAYPAL, SAMSUNG_PAY, etc.)
     */
    TipoCarteira getTipoCarteira();
}
