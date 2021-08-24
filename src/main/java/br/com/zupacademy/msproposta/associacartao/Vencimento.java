package br.com.zupacademy.msproposta.associacartao;

import br.com.zupacademy.msproposta.associacartao.externo.VencimentoResponse;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_vencimento")
public class Vencimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String apiId;
    private Integer dia;
    private LocalDateTime dataDeCriacao;

    @Deprecated
    public Vencimento(){}

    public Vencimento(VencimentoResponse response) {
        this.apiId = response.getId();
        this.dia = response.getDia();
        this.dataDeCriacao = response.getDataDeCriacao();
    }
}
