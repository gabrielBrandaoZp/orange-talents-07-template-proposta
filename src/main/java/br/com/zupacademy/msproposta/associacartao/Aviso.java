package br.com.zupacademy.msproposta.associacartao;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "tb_aviso")
public class Aviso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate validoAte;
    private String destino;

    @ManyToOne
    private Cartao cartao;

    @Deprecated
    public Aviso(){}

    public Aviso(AvisoResponse response) {
        this.validoAte = response.getValidoAte();
        this.destino = response.getDestino();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aviso aviso = (Aviso) o;
        return id.equals(aviso.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
