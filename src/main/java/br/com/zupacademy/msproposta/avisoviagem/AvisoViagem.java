package br.com.zupacademy.msproposta.avisoviagem;

import br.com.zupacademy.msproposta.associacartao.Cartao;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "tb_aviso_viagem")
public class AvisoViagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String destino;
    private LocalDate terminaEm;
    private LocalDateTime criadoEm;
    private String ip;
    private String userAgent;

    @ManyToOne
    private Cartao cartao;

    @Deprecated
    public AvisoViagem() {}

    public AvisoViagem(String destino, LocalDate terminaEm, String ip, String userAgent, Cartao cartao) {
        this.destino = destino;
        this.terminaEm = terminaEm;
        this.criadoEm = LocalDateTime.now();
        this.ip = ip;
        this.userAgent = userAgent;
        this.cartao = cartao;
    }

    public String getDestino() {
        return destino;
    }

    public LocalDate getTerminaEm() {
        return terminaEm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AvisoViagem that = (AvisoViagem) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
