package br.com.zupacademy.msproposta.novaproposta;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "tb_proposta")
public class Proposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String documento;
    private String email;
    private String nome;
    private String endereco;
    private BigDecimal salario;

    @Deprecated
    public Proposta() {}

    public Proposta(String documento, String email, String nome, String endereco, BigDecimal salario) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
    }

    public Long getId() {
        return id;
    }
}
