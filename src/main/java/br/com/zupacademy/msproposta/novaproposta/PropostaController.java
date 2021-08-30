package br.com.zupacademy.msproposta.novaproposta;

import br.com.zupacademy.msproposta.metricas.MetricasPersonalizadas;
import br.com.zupacademy.msproposta.novaproposta.externo.AnaliseFinanceiraService;
import br.com.zupacademy.msproposta.utils.compartilhado.Metodos;
import br.com.zupacademy.msproposta.utils.exceptions.ApiErrorException;
import io.opentracing.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class PropostaController {
    private final Logger logger = LoggerFactory.getLogger(PropostaController.class);

    private final PropostaRepository propostaRepository;
    private final AnaliseFinanceiraService analiseFinanceiraService;
    private final MetricasPersonalizadas metricasPersonalizadas;
    private final Tracer tracer;

    public PropostaController(PropostaRepository propostaRepository, AnaliseFinanceiraService analiseFinanceiraService,
                              MetricasPersonalizadas metricasPersonalizadas, Tracer tracer) {
        this.propostaRepository = propostaRepository;
        this.analiseFinanceiraService = analiseFinanceiraService;
        this.metricasPersonalizadas = metricasPersonalizadas;
        this.tracer = tracer;
    }

    @PostMapping("/propostas")
    public ResponseEntity<Void> novaProposta(@RequestBody @Valid NovaPropostaRequest request, UriComponentsBuilder uriBuilder) {
        tracer.activeSpan().setTag("user.email", "joaozinho@email.com");

        logger.info("method=novaProposta, msg=cadastrando nova proposta");

        DocumentoLimpo documentoLimpo = new DocumentoLimpo(request.getDocumento());
        String hashedDoc = documentoLimpo.hash();
        Optional<Proposta> possivelProposta = propostaRepository.findByDocumentoHash(hashedDoc);
        if (possivelProposta.isPresent()) {
            logger.error("method=novaProposta, msg=a proposta não pode ser criada, documento: {} já existe na base",
                    request.getDocumento());
            throw new ApiErrorException(HttpStatus.UNPROCESSABLE_ENTITY, "Documento já existe");
        }

        Proposta proposta = request.paraProposta();
        propostaRepository.save(proposta);

        analiseFinanceiraService.solicitarAnaliseFinanceira(proposta, request.getDocumento(), propostaRepository::save);

        logger.info("method=novaProposta, msg=proposta: {} para o documento: {} cadastrada com sucesso",
                proposta.getId(), proposta.getDocumentoEncriptado());

        metricasPersonalizadas.contadorPropostasCriadas();

        URI uri = Metodos.criarUri(uriBuilder, "/api/v1/propostas/{id}", proposta.getId());
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/propostas/{id}")
    public ResponseEntity<PropostaResponse> propostaPorId(@PathVariable Long id) {
        tracer.activeSpan().setBaggageItem("cartao.id", id.toString());

        logger.info("method=propostaPorId, msg=buscando proposta: {}", id);
        Optional<Proposta> possivelProposta = propostaRepository.findById(id);
        if (possivelProposta.isPresent()) {
            Proposta proposta = possivelProposta.get();
            metricasPersonalizadas.timerConsultarPropostas(id);
            return ResponseEntity.ok(new PropostaResponse(proposta));
        }

        logger.error("method=propostaPorId, msg=proposta: {} não encontrada", id);
        return ResponseEntity.notFound().build();
    }
}
