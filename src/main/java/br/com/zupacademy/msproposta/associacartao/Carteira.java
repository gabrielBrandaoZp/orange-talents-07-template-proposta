package br.com.zupacademy.msproposta.associacartao;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "tb_carteira")
public class Carteira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String apiId;
    private String email;
    private LocalDateTime associadoEm;
    private String emissor;

    @ManyToOne
    private Cartao cartao;

    @Deprecated
    public Carteira(){}

    public Carteira(CarteiraResponse response) {
        this.apiId = response.getId();
        this.email = response.getEmail();
        this.associadoEm = response.getAssociadoEm();
        this.emissor = response.getEmissor();
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
