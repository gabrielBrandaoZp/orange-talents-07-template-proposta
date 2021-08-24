package br.com.zupacademy.msproposta.novaproposta.externo;

import br.com.zupacademy.msproposta.novaproposta.Proposta;
import br.com.zupacademy.msproposta.novaproposta.StatusProposta;
import br.com.zupacademy.msproposta.utils.transacional.ExecutorDeTransacao;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.function.UnaryOperator;

@Service
public class AnaliseFinanceiraService {
    private final Logger logger = LoggerFactory.getLogger(AnaliseFinanceiraService.class);

    private final ApiAnaliseFinanceira apiAnaliseFinanceira;
    private final ExecutorDeTransacao executorDeTransacao;

    public AnaliseFinanceiraService(ApiAnaliseFinanceira apiAnaliseFinanceira, ExecutorDeTransacao executorDeTransacao) {
        this.apiAnaliseFinanceira = apiAnaliseFinanceira;
        this.executorDeTransacao = executorDeTransacao;
    }

    public void solicitarAnaliseFinanceira(Proposta proposta, String documentoSemCriptografia, UnaryOperator<Proposta> salvaProposta) {
        executorDeTransacao.executaNaTransacao(() -> {
            try {
                SolicitacaoAnaliseResponse res = apiAnaliseFinanceira
                        .verificaAnaliseFinanceira(new SolicitacaoAnaliseRequest(proposta, documentoSemCriptografia));
                StatusSolicitacao statusSolicitacao = res.getResultadoSolicitacao();
                proposta.setStatusProposta(statusSolicitacao.normaliza());
                logger.info("method=solicitarAnaliseFinanceira, msg=analise financeira positiva, a proposta foi aprovada com status: {}", statusSolicitacao);
                salvaProposta.apply(proposta);
            } catch (FeignException fe) {
                proposta.setStatusProposta(StatusProposta.NAO_ELEGIVEL);
                logger.warn("method=solicitarAnaliseFinanceira, msg=analise financeira negativa, a proposta foi recusada com status: {}", StatusProposta.NAO_ELEGIVEL);
            }
        });
    }
}
