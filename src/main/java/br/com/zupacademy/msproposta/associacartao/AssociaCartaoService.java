package br.com.zupacademy.msproposta.associacartao;

import br.com.zupacademy.msproposta.associacartao.externo.ApiContas;
import br.com.zupacademy.msproposta.associacartao.externo.SolicitacaoCartaoResponse;
import br.com.zupacademy.msproposta.novaproposta.Proposta;
import br.com.zupacademy.msproposta.novaproposta.StatusProposta;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Service
@EnableScheduling
public class AssociaCartaoService {

    private final Logger logger = LoggerFactory.getLogger(AssociaCartaoService.class);
    private final ApiContas apiContas;
    private final EntityManager entityManager;

    public AssociaCartaoService(ApiContas apiContas, EntityManager entityManager) {
        this.apiContas = apiContas;
        this.entityManager = entityManager;
    }

    @Transactional
    @Scheduled(fixedDelayString = "${periodicidade.associa.cartao}")
    public void associaCartaoAProposta() {
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
    }
}
