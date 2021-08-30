package br.com.zupacademy.msproposta.novaproposta;

import org.apache.commons.codec.digest.DigestUtils;
import org.jasypt.util.text.AES256TextEncryptor;

public class DocumentoLimpo {

    private final AES256TextEncryptor aes256TextEncryptor;

    private final String documento;

    public DocumentoLimpo(String documento) {
        this.documento = documento;
        this.aes256TextEncryptor = new AES256TextEncryptor();
        aes256TextEncryptor.setPassword("coxinha123");
    }

    public String encrypt() {
        return aes256TextEncryptor.encrypt(this.documento);
    }

    public String hash() {
        return DigestUtils.sha256Hex(this.documento);
    }
}
