package br.com.zupacademy.msproposta.novaproposta;

import br.com.zupacademy.msproposta.utils.exceptions.ApiErrorException;
import br.com.zupacademy.msproposta.utils.handler.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/propostas")
public class PropostaController {

    private final PropostaRepository propostaRepository;

    public PropostaController(PropostaRepository propostaRepository) {
        this.propostaRepository = propostaRepository;
    }

    @PostMapping
    public ResponseEntity<Void> novaProposta(@RequestBody @Valid NovaPropostaRequest request, UriComponentsBuilder uriBuilder) {
        Optional<Proposta> possivelProposta = propostaRepository.findByDocumento(request.getDocumento());
        if (possivelProposta.isPresent()) {
            throw new ApiErrorException(HttpStatus.UNPROCESSABLE_ENTITY, "Documento já existe");
        }

        Proposta proposta = request.paraProposta();
        propostaRepository.save(proposta);

        URI uri = uriBuilder
                .path("/api/v1/propostas/{id}")
                .buildAndExpand(proposta.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }
}
