package br.com.zupacademy.msproposta.criarbiometria;

import br.com.zupacademy.msproposta.associacartao.Cartao;
import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class BiometriaRequest {

    @NotBlank
    @Pattern(regexp = "^(?:[A-Za-z\\d+/]{4})*(?:[A-Za-z\\d+/]{3}=|[A-Za-z\\d+/]{2}==)?$")
    private final String fingerprint;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public BiometriaRequest(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public Biometria paraBiometria(Cartao cartao) {
        return new Biometria(fingerprint, cartao);
    }
}
