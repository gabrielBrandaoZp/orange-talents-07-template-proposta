package br.com.zupacademy.msproposta.novaproposta;

import br.com.zupacademy.msproposta.associacartao.Cartao;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "tb_proposta")
public class Proposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String documentoEncriptado;
    private String documentoHash;
    private String email;
    private String nome;
    private String endereco;
    private BigDecimal salario;

    @Enumerated(EnumType.STRING)
    private StatusProposta statusProposta;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Cartao cartao;

    @Deprecated
    public Proposta() {}

    public Proposta(DocumentoLimpo documentoLimpo, String email, String nome, String endereco, BigDecimal salario) {
        this.documentoEncriptado = documentoLimpo.encrypt();
        this.documentoHash = documentoLimpo.hash();
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
    }

    public Long getId() {
        return id;
    }

    public String getDocumentoEncriptado() {
        return documentoEncriptado;
    }

    public String getDocumentoHash() {
        return documentoHash;
    }

    public String documentoDescriptografado() {
        Criptografia documentoCriptografado = new Criptografia(this.documentoEncriptado);
        return documentoCriptografado.documentoDescriptografado();
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getEndereco() {
        return endereco;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public StatusProposta getStatusProposta() {
        return statusProposta;
    }

    public void setStatusProposta(StatusProposta statusProposta) {
        this.statusProposta = statusProposta;
    }

    public void associaCartao(Cartao cartao) {
        this.cartao = cartao;
    }
}
