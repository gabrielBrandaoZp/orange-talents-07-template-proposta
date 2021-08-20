package br.com.zupacademy.msproposta.bloqueiocartao;

import br.com.zupacademy.msproposta.associacartao.Cartao;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "tb_bloqueio")
public class Bloqueio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime bloqueadoEm;
    private String ip;
    private String userAgent;

    @ManyToOne
    private Cartao cartao;

    @Deprecated
    public Bloqueio() {
    }

    public Bloqueio(String ip, String userAgent, Cartao cartao) {
        this.bloqueadoEm = LocalDateTime.now();
        this.ip = ip;
        this.userAgent = userAgent;
        this.cartao = cartao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bloqueio bloqueio = (Bloqueio) o;
        return id.equals(bloqueio.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
