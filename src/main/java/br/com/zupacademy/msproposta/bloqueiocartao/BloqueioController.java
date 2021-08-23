package br.com.zupacademy.msproposta.bloqueiocartao;

import br.com.zupacademy.msproposta.associacartao.Cartao;
import br.com.zupacademy.msproposta.associacartao.CartaoService;
import br.com.zupacademy.msproposta.associacartao.CartaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class BloqueioController {
    private final Logger logger = LoggerFactory.getLogger(BloqueioController.class);

    private final BloqueioRepository bloqueioRepository;
    private final CartaoRepository cartaoRepository;
    private final CartaoService cartaoService;

    public BloqueioController(BloqueioRepository bloqueioRepository, CartaoRepository cartaoRepository, CartaoService cartaoService) {
        this.bloqueioRepository = bloqueioRepository;
        this.cartaoRepository = cartaoRepository;
        this.cartaoService = cartaoService;
    }

    @PostMapping("/cartoes/{idCartao}/bloqueios")
    public ResponseEntity<Void> bloquearCartao(@PathVariable Long idCartao, @RequestHeader(value = "User-Agent") String userAgent,
                                               @RequestHeader(value = "ip") String ip) {
        logger.info("method=bloquearCartao, msg=bloqueando cartão de id {}", idCartao);

        Optional<Cartao> possivelCartao = cartaoRepository.findById(idCartao);
        if (possivelCartao.isPresent()) {

            if (Objects.isNull(userAgent)) return ResponseEntity.badRequest().build();

            if (Objects.isNull(ip)) return ResponseEntity.badRequest().build();

            Optional<Bloqueio> possivelBloqueio = bloqueioRepository.findByCartaoId(idCartao);
            if(possivelBloqueio.isPresent()) return ResponseEntity.unprocessableEntity().build();

            Cartao cartao = possivelCartao.get();
            cartaoService.bloquearCartao(cartao, ip, userAgent);

            if (!cartao.isCartaoBloqueado()) return ResponseEntity.badRequest().build();

            return ResponseEntity.ok().build();
        }

        logger.error("method=bloquearCartao, msg=Cartao não encontrado");
        return ResponseEntity.notFound().build();
    }
}
