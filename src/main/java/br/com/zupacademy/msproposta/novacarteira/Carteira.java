package br.com.zupacademy.msproposta.novacarteira;

import br.com.zupacademy.msproposta.associacartao.Cartao;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tb_carteira")
public class Carteira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Cartao cartao;

    @Enumerated(EnumType.STRING)
    private TipoCarteira tipo;

    private String email;

    @Deprecated
    public Carteira(){}

    public Carteira(Cartao cartao, TipoCarteira tipo, String email) {
        this.cartao = cartao;
        this.tipo = tipo;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getTipo() {
        return tipo.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Carteira carteira = (Carteira) o;
        return id.equals(carteira.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
