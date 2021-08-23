package br.com.zupacademy.msproposta.avisoviagem;

import br.com.zupacademy.msproposta.associacartao.Cartao;
import br.com.zupacademy.msproposta.associacartao.CartaoRepository;
import br.com.zupacademy.msproposta.associacartao.CartaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class AvisoViagemController {
    private final Logger logger = LoggerFactory.getLogger(AvisoViagemController.class);

    private final CartaoService cartaoService;
    private final CartaoRepository cartaoRepository;

    public AvisoViagemController(CartaoService cartaoService, CartaoRepository cartaoRepository) {
        this.cartaoService = cartaoService;
        this.cartaoRepository = cartaoRepository;
    }

    @PostMapping("/cartoes/{id}/avisos-viagens")
    public ResponseEntity<Map<String, Object>> cadastrarAviso(@PathVariable Long id, @RequestBody @Valid AvisoViagemRequest request,
                                               @RequestHeader(value = "User-Agent") String userAgent,
                                               @RequestHeader(value = "ip") String ip) {
        logger.info("method=cadastarAviso, msg=cadastrando aviso para o cartão: {}", id);

        Optional<Cartao> possivelCartao = cartaoRepository.findById(id);
        if (possivelCartao.isPresent()) {
            Cartao cartao = possivelCartao.get();
            AvisoViagem avisoViagem = request.paraAvisoViagem(ip, userAgent, cartao);

            if (!cartaoService.avisarViagemCartao(cartao, avisoViagem)){
                return ResponseEntity
                        .unprocessableEntity()
                        .body(Map.of("aviso", "O cartão já tem um aviso cadastrado"));
            }

            logger.info("method=cadastarAviso, msg=cadastro de aviso para o cartão: {} realizado com sucesso", id);
            return ResponseEntity.ok().build();
        }

        logger.error("method=cadastarAviso, msg=cartão não encontrado: {}", id);
        return ResponseEntity.notFound().build();
    }
}
