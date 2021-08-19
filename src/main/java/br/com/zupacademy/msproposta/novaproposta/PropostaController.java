package br.com.zupacademy.msproposta.novaproposta;

import br.com.zupacademy.msproposta.novaproposta.externo.ApiAnaliseFinanceira;
import br.com.zupacademy.msproposta.novaproposta.externo.SolicitacaoAnaliseRequest;
import br.com.zupacademy.msproposta.novaproposta.externo.SolicitacaoAnaliseResponse;
import br.com.zupacademy.msproposta.novaproposta.externo.StatusSolicitacao;
import br.com.zupacademy.msproposta.utils.exceptions.ApiErrorException;
import br.com.zupacademy.msproposta.utils.transacional.ExecutorDeTransacao;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/propostas")
public class PropostaController {

    private final PropostaRepository propostaRepository;
    private final ExecutorDeTransacao executorDeTransacao;
    private final ApiAnaliseFinanceira apiAnaliseFinanceira;
    private final Logger logger = LoggerFactory.getLogger(PropostaController.class);

    public PropostaController(PropostaRepository propostaRepository, ExecutorDeTransacao executorDeTransacao, ApiAnaliseFinanceira apiAnaliseFinanceira) {
        this.propostaRepository = propostaRepository;
        this.executorDeTransacao = executorDeTransacao;
        this.apiAnaliseFinanceira = apiAnaliseFinanceira;
    }

    @PostMapping
    public ResponseEntity<Void> novaProposta(@RequestBody @Valid NovaPropostaRequest request, UriComponentsBuilder uriBuilder) {
        logger.info("method=novaProposta, msg=cadastrando nova proposta");

        Optional<Proposta> possivelProposta = propostaRepository.findByDocumento(request.getDocumento());
        if (possivelProposta.isPresent()) {
            logger.error("method=novaProposta, msg=a proposta não pode ser criada, documento: {} já existe na base",
                    request.getDocumento());
            throw new ApiErrorException(HttpStatus.UNPROCESSABLE_ENTITY, "Documento já existe");
        }

        Proposta proposta = request.paraProposta();
        executorDeTransacao.executaNaTransacao(() -> propostaRepository.save(proposta));

        tratarSolicitacaoAnaliseFinanceira(proposta);

        URI uri = uriBuilder
                .path("/api/v1/propostas/{id}")
                .buildAndExpand(proposta.getId())
                .toUri();

        logger.info("method=novaProposta, msg=proposta: {} para o documento: {} cadastrada com sucesso",
                proposta.getId(), proposta.getDocumento());
        return ResponseEntity.created(uri).build();
    }

    private void tratarSolicitacaoAnaliseFinanceira(Proposta proposta) {
        SolicitacaoAnaliseRequest solicitacaoRequest = new SolicitacaoAnaliseRequest(proposta);
        StatusSolicitacao statusSolicitacao;
        try{
            SolicitacaoAnaliseResponse solicitacaoResponse = apiAnaliseFinanceira.verificaAnaliseFinanceira(solicitacaoRequest);
            statusSolicitacao = solicitacaoResponse.getResultadoSolicitacao();
            proposta.setStatusProposta(statusSolicitacao.normaliza());
            executorDeTransacao.executaNaTransacao(() -> {
                propostaRepository.save(proposta);
            });
        } catch (FeignException fe) {
            executorDeTransacao.executaNaTransacao(() -> proposta.setStatusProposta(StatusProposta.NAO_ELEGIVEL));
        }
    }

    @Transactional
    @GetMapping("/{id}")
    public ResponseEntity<PropostaResponse> propostaPorId(@PathVariable Long id) {
        logger.info("method=propostaPorId, msg=buscando proposta: {}", id);
        Optional<Proposta> possivelProposta = propostaRepository.findById(id);
        if (possivelProposta.isPresent()) {
            Proposta proposta = possivelProposta.get();
            return ResponseEntity.ok(new PropostaResponse(proposta));
        }

        logger.error("method=propostaPorId, msg=proposta: {} não encontrada", id);
        return ResponseEntity.notFound().build();
    }
}
