package br.com.zupacademy.msproposta.criarbiometria;

import br.com.zupacademy.msproposta.associacartao.Cartao;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "tb_biometria")
public class Biometria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fingerprint;
    private LocalDateTime associadaEm;

    @ManyToOne
    private Cartao cartao;

    @Deprecated
    public Biometria() {}

    public Biometria(String fingerprint, Cartao cartao) {
        this.fingerprint = fingerprint;
        this.cartao = cartao;
        this.associadaEm = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Biometria biometria = (Biometria) o;
        return id.equals(biometria.id) && fingerprint.equals(biometria.fingerprint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fingerprint);
    }
}
