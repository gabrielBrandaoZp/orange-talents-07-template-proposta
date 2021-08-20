package br.com.zupacademy.msproposta.avisoviagem;

import br.com.zupacademy.msproposta.associacartao.Cartao;
import br.com.zupacademy.msproposta.criarbiometria.CartaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/avisos-viagens/cartoes")
public class AvisoViagemController {
    private final Logger logger = LoggerFactory.getLogger(AvisoViagemController.class);

    private final AvisoViagemRepository avisoViagemRepository;
    private final CartaoRepository cartaoRepository;

    public AvisoViagemController(AvisoViagemRepository avisoViagemRepository, CartaoRepository cartaoRepository) {
        this.avisoViagemRepository = avisoViagemRepository;
        this.cartaoRepository = cartaoRepository;
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> cadastrarAviso(@PathVariable Long id, @RequestBody @Valid AvisoViagemRequest request,
                                               @RequestHeader(value = "User-Agent") String userAgent,
                                               @RequestHeader(value = "ip") String ip) {
        logger.info("method=cadastarAviso, msg=cadastrando aviso para o cartão: {}", id);

        Optional<Cartao> possivelCartao = cartaoRepository.findById(id);
        if (possivelCartao.isPresent()) {

            if (Objects.isNull(userAgent)) return ResponseEntity.unprocessableEntity().build();

            if (Objects.isNull(ip)) return ResponseEntity.unprocessableEntity().build();

            Cartao cartao = possivelCartao.get();
            AvisoViagem avisoViagem = request.paraAvisoViagem(userAgent, ip, cartao);
            avisoViagemRepository.save(avisoViagem);

            logger.info("method=cadastarAviso, msg=cadastro de aviso para o cartão: {} realizado com sucesso", id);
            return ResponseEntity.ok().build();
        }

        logger.error("method=cadastarAviso, msg=cartão não encontrado: {}", id);
        return ResponseEntity.notFound().build();
    }
}
