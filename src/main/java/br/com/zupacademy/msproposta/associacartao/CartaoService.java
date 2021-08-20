package br.com.zupacademy.msproposta.associacartao;

import br.com.zupacademy.msproposta.associacartao.externo.ApiContas;
import br.com.zupacademy.msproposta.associacartao.externo.BloqueioRequest;
import br.com.zupacademy.msproposta.associacartao.externo.BloqueioResponse;
import br.com.zupacademy.msproposta.associacartao.externo.SolicitacaoCartaoResponse;
import br.com.zupacademy.msproposta.bloqueiocartao.Bloqueio;
import br.com.zupacademy.msproposta.novaproposta.Proposta;
import br.com.zupacademy.msproposta.novaproposta.StatusProposta;
import br.com.zupacademy.msproposta.utils.transacional.ExecutorDeTransacao;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Service
@EnableScheduling
public class CartaoService {

    private final Logger logger = LoggerFactory.getLogger(CartaoService.class);
    private final ApiContas apiContas;
    private final EntityManager entityManager;
    private final ExecutorDeTransacao executorDeTransacao;

    public CartaoService(ApiContas apiContas, EntityManager entityManager, ExecutorDeTransacao executorDeTransacao) {
        this.apiContas = apiContas;
        this.entityManager = entityManager;
        this.executorDeTransacao = executorDeTransacao;
    }

    @Scheduled(fixedDelayString = "${periodicidade.associa.cartao}")
    public void associaCartaoAProposta() {
        executorDeTransacao.executaNaTransacao(() -> {

            //Using entity manager because JPQL does not supports LIMIT to the queries
            TypedQuery<Proposta> query = entityManager.createQuery("SELECT DISTINCT p FROM Proposta p WHERE p.statusProposta = :pstatus AND p.cartao IS NULL", Proposta.class);
            query.setParameter("pstatus", StatusProposta.ELEGIVEL);
            query.setMaxResults(50);

            logger.info("method=associaCartaoAProposta, msg=associando cartões a proposta");
            List<Proposta> propostasElegiveis = query.getResultList();
            propostasElegiveis.forEach(proposta -> {
                        try {
                            SolicitacaoCartaoResponse res = apiContas.gerarCartao(proposta.getId().toString());
                            proposta.associaCartao(new Cartao(res));
                            logger.info("method=associaCartaoAProposta, msg=cartao associado a proposta de id: {}", proposta.getId());
                            entityManager.merge(proposta);
                        } catch (FeignException fe) {
                            logger.error("method=associaCartaoAProposta, msg=cartão não associado a proposta, aguardar proxima solicitação");
                        }
                    }
            );
        });
    }

    public void bloquearCartao(Cartao cartao, String ip, String userAgent) {
        executorDeTransacao.executaNaTransacao(() -> {
            try {
                BloqueioResponse res = apiContas.bloquearCartao(cartao.getNumeroCartao(), new BloqueioRequest("propostas-webservice"));
                Bloqueio bloqueio = new Bloqueio(ip, userAgent, cartao);
                cartao.bloqueiaCartao();
                entityManager.persist(bloqueio);
                logger.info("method=bloquearCartao, msg=O status do cartão agora é: {}", res.getResultado());
            } catch (FeignException.UnprocessableEntity feu) {
                logger.error("method=bloquearCartao, msg=Não foi possivel bloquear o cartão: {}, pois o mesmo já se encontra bloqueado", cartao.getNumeroCartao());
            } catch (FeignException fe) {
                logger.error("method=bloquearCartao, msg=Não foi possivel bloquear o cartão: {}", cartao.getNumeroCartao());
            }
        });
    }
}
