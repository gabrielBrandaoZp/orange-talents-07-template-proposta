package br.com.zupacademy.msproposta.novacarteira;

import br.com.zupacademy.msproposta.associacartao.Cartao;
import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class NovaCarteiraPaypalRequest implements NovaCarteiraRequest {

    @NotBlank
    @Email
    private String email;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public NovaCarteiraPaypalRequest(String email) {
        this.email = email;
    }

    @Override
    public Carteira paraCarteira(Cartao cartao) {
        return new Carteira(cartao, TipoCarteira.PAYPAL, email);
    }

    @Override
    public String getEmailRequest() {
        return email;
    }

    @Override
    public TipoCarteira getTipoCarteira() {
        return TipoCarteira.PAYPAL;
    }


}
