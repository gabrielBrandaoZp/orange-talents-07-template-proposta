package br.com.zupacademy.msproposta.associacartao;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "tb_parcela")
public class Parcela {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String apiId;
    private Integer quantidade;
    private BigDecimal valor;

    @ManyToOne
    private Cartao cartao;

    @Deprecated
    public Parcela(){}

    public Parcela(ParcelaResponse response) {
        this.apiId = response.getId();
        this.quantidade = response.getQuantidade();
        this.valor = response.getValor();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Parcela parcela = (Parcela) o;
        return id.equals(parcela.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
