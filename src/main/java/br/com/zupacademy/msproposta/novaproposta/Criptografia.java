package br.com.zupacademy.msproposta.novaproposta;

import org.jasypt.util.text.AES256TextEncryptor;

public class Criptografia {

    private AES256TextEncryptor aes256TextEncryptor;

    private String documento;

    public Criptografia(String documento) {
        this.documento = documento;
        this.aes256TextEncryptor = new AES256TextEncryptor();
        aes256TextEncryptor.setPassword("coxinha123");
    }

    public String documentoDescriptografado() {
        return aes256TextEncryptor.decrypt(documento);
    }
}
