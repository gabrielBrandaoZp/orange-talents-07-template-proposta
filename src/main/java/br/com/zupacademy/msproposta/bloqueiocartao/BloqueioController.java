package br.com.zupacademy.msproposta.bloqueiocartao;

import br.com.zupacademy.msproposta.associacartao.Cartao;
import br.com.zupacademy.msproposta.criarbiometria.CartaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/bloqueios/cartoes")
public class BloqueioController {
    private final Logger logger = LoggerFactory.getLogger(BloqueioController.class);

    private final BloqueioRepository bloqueioRepository;
    private final CartaoRepository cartaoRepository;

    public BloqueioController(BloqueioRepository bloqueioRepository, CartaoRepository cartaoRepository) {
        this.bloqueioRepository = bloqueioRepository;
        this.cartaoRepository = cartaoRepository;
    }

    @PostMapping("/{idCartao}")
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
            cartao.bloqueiaCartao();
            Bloqueio bloqueio = new Bloqueio(ip, userAgent, cartao);
            bloqueioRepository.save(bloqueio);

            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }
}
