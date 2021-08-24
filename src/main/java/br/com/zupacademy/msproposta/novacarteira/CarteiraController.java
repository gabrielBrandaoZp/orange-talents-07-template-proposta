package br.com.zupacademy.msproposta.novacarteira;

import br.com.zupacademy.msproposta.associacartao.Cartao;
import br.com.zupacademy.msproposta.associacartao.CartaoRepository;
import br.com.zupacademy.msproposta.associacartao.CartaoService;
import br.com.zupacademy.msproposta.novaproposta.PropostaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class CarteiraController {

    private final CarteiraRepository carteiraRepository;
    private final CartaoRepository cartaoRepository;
    private final PropostaRepository propostaRepository;
    private final CartaoService cartaoService;

    public CarteiraController(CarteiraRepository carteiraRepository, CartaoRepository cartaoRepository, PropostaRepository propostaRepository, CartaoService cartaoService) {
        this.carteiraRepository = carteiraRepository;
        this.cartaoRepository = cartaoRepository;
        this.propostaRepository = propostaRepository;
        this.cartaoService = cartaoService;
    }

    @PostMapping("/cartoes/{id}/carteiras/paypal")
    public ResponseEntity<Map<String, Object>> cadastrarCarteiraPaypal(@PathVariable Long id, @RequestBody @Valid NovaCarteiraPaypalRequest request,
                                                                       UriComponentsBuilder uriBuilder) {
        return processaCadastro(id, request, uriBuilder);
    }

    @PostMapping("/cartoes/{id}/carteiras/samsungpay")
    public ResponseEntity<Map<String, Object>> cadastrarCarteiraPaypal(@PathVariable Long id, @RequestBody @Valid NovaCarteiraSamsungPayRequest request,
                                                                       UriComponentsBuilder uriBuilder) {
        return processaCadastro(id, request, uriBuilder);
    }

    private ResponseEntity<Map<String, Object>> processaCadastro(Long id, NovaCarteiraRequest request, UriComponentsBuilder uriBuilder) {
        Optional<Cartao> possivelCartao = cartaoRepository.findById(id);
        if (possivelCartao.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Cartao cartao = possivelCartao.get();

        boolean isEmailDonoCartao = propostaRepository.existsByCartaoAndEmail(cartao, request.getEmailRequest());
        if (!isEmailDonoCartao) {
            return ResponseEntity
                    .unprocessableEntity()
                    .body(Map.of("messages", "O e-mail informado não é o mesmo do cartão cadastrado"));
        }

        boolean isCarteiraJaCadastrada = carteiraRepository.existsByCartaoAndTipo(cartao, request.getTipoCarteira());
        if (isCarteiraJaCadastrada) {
            return ResponseEntity
                    .unprocessableEntity()
                    .body(Map.of("messages", "Carteira do tipo informado já possui esse cartão associado"));
        }

        Carteira carteira = request.paraCarteira(cartao);

        boolean resultado = cartaoService.cadastarCarteira(cartao, carteira);
        if (!resultado) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("messages", "Erro ao tentar cadastrar a carteira, tente novamente"));
        }

        URI uri = criarUriCarteira(uriBuilder, carteira.getId(), request.getTipoCarteira());
        return ResponseEntity.created(uri).build();
    }

    private URI criarUriCarteira(UriComponentsBuilder uriBuilder, Long carteiraId, TipoCarteira tipoCarteira) {
        return uriBuilder
                .path("/carteira/" + tipoCarteira.toString().toLowerCase() + "/{id}")
                .buildAndExpand(carteiraId)
                .toUri();
    }
}
