package br.com.zupacademy.msproposta.associacartao;

import br.com.zupacademy.msproposta.associacartao.externo.*;
import br.com.zupacademy.msproposta.avisoviagem.AvisoViagem;
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
                cartao.bloqueiaCartao(new Bloqueio(ip, userAgent, cartao));
                entityManager.merge(cartao);
                logger.info("method=bloquearCartao, msg=O status do cartão agora é: {}", res.getResultado());
            } catch (FeignException.UnprocessableEntity feu) {
                logger.error("method=bloquearCartao, msg=Não foi possivel bloquear o cartão: {}, pois o mesmo já se encontra bloqueado", cartao.getNumeroCartao());
            } catch (FeignException fe) {
                logger.error("method=bloquearCartao, msg=Não foi possivel bloquear o cartão: {}", cartao.getNumeroCartao());
            }
        });
    }

    public boolean avisarViagemCartao(Cartao cartao, AvisoViagem aviso) {
        try {
            executorDeTransacao.executaNaTransacao(() -> {
                AvisoViagemResponseClient res = apiContas.avisarViagemCartao(cartao.getNumeroCartao(), new AvisoViagemRequestClient(aviso.getDestino(), aviso.getTerminaEm()));
                cartao.avisarViagem(aviso);
                entityManager.merge(cartao);
                logger.info("method=avisarViagemCartao, msg=O aviso de cartão foi feito com sucesso, status: {}", res.getResultado());
            });
            return true;
        } catch (FeignException.UnprocessableEntity feu) {
            logger.error("method=avisarViagemCartao, msg=Não foi possivel realizar o aviso do cartão: {}, pois o mesmo já se encontra sobre aviso", cartao.getNumeroCartao());
        } catch (FeignException fe) {
            logger.error("method=avisarViagemCartao, msg=Não foi possivel realizar o aviso do cartão: {}", cartao.getNumeroCartao());
        }

        return false;
    }
}
