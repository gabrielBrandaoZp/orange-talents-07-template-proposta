package br.com.zupacademy.msproposta.associacartao;

import java.time.LocalDateTime;

public class CarteiraResponse {

    private String id;
    private String email;
    private LocalDateTime associadoEm;
    private String emissor;

    @Deprecated
    public CarteiraResponse(){}

    public CarteiraResponse(String id, String email, LocalDateTime associadoEm, String emissor) {
        this.id = id;
        this.email = email;
        this.associadoEm = associadoEm;
        this.emissor = emissor;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getAssociadoEm() {
        return associadoEm;
    }

    public String getEmissor() {
        return emissor;
    }
}
