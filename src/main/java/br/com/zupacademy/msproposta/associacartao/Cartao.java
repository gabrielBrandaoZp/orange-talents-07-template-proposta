package br.com.zupacademy.msproposta.associacartao;

import br.com.zupacademy.msproposta.associacartao.externo.SolicitacaoCartaoResponse;
import br.com.zupacademy.msproposta.avisoviagem.AvisoViagem;
import br.com.zupacademy.msproposta.bloqueiocartao.Bloqueio;
import br.com.zupacademy.msproposta.criarbiometria.Biometria;
import br.com.zupacademy.msproposta.novacarteira.Carteira;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "tb_cartao")
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String numeroCartao;
    private String nomeTitular;
    private BigDecimal limite;
    private LocalDateTime emitidoEm;

    @OneToMany(mappedBy = "cartao", cascade = CascadeType.MERGE)
    private Set<Bloqueio> bloqueios;

    @OneToMany(mappedBy = "cartao", cascade = CascadeType.MERGE)
    private Set<AvisoViagem> avisos;

    @OneToMany(mappedBy = "cartao")
    private Set<Carteira> carteiras;

    @OneToMany(mappedBy = "cartao", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Parcela> parcelas;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Renegociacao renegociacao;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Vencimento vencimento;

    @OneToMany(mappedBy = "cartao")
    private Set<Biometria> biometrias;

    @Enumerated(EnumType.STRING)
    private StatusCartao statusCartao;

    private String idProposta;

    @Deprecated
    public Cartao() {
    }

    public Cartao(SolicitacaoCartaoResponse response) {
        this.statusCartao = StatusCartao.ATIVO;
        this.numeroCartao = response.getId();
        this.nomeTitular = response.getTitular();
        this.limite = response.getLimite();
        this.emitidoEm = response.getEmitidoEm();

        if (!response.getParcelas().isEmpty()) {
            this.parcelas = response.getParcelas().stream().map(Parcela::new).collect(Collectors.toSet());
        }

        if (!Objects.isNull(response.getRenegociacao())) {
            this.renegociacao = new Renegociacao(response.getRenegociacao());
        }

        if (!Objects.isNull(response.getVencimento())) {
            this.vencimento = new Vencimento(response.getVencimento());
        }

        this.idProposta = response.getIdProposta();
    }

    public void bloqueiaCartao(Bloqueio bloqueio) {
        this.bloqueios.add(bloqueio);
        this.statusCartao = StatusCartao.BLOQUEADO;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public boolean isCartaoBloqueado() {
        return this.statusCartao.equals(StatusCartao.BLOQUEADO);
    }

    public void avisarViagem(AvisoViagem avisoViagem) {
        this.avisos.add(avisoViagem);
    }
}
