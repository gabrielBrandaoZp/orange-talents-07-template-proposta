package br.com.zupacademy.msproposta.associacartao.externo;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class NovaCarteiraRequestClient {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String carteira;

    public NovaCarteiraRequestClient(String email, String carteira) {
        this.email = email;
        this.carteira = carteira;
    }

    public String getEmail() {
        return email;
    }

    public String getCarteira() {
        return carteira;
    }
}
