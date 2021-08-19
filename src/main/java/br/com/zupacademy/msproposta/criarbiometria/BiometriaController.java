package br.com.zupacademy.msproposta.criarbiometria;

import br.com.zupacademy.msproposta.associacartao.Cartao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/biometrias")
public class BiometriaController {

    private final CartaoRepository cartaoRepository;
    private final BiometriaRepository biometriaRepository;
    private final Logger logger = LoggerFactory.getLogger(BiometriaController.class);

    public BiometriaController(CartaoRepository cartaoRepository, BiometriaRepository biometriaRepository) {
        this.cartaoRepository = cartaoRepository;
        this.biometriaRepository = biometriaRepository;
    }

    @PostMapping("/cartoes/{idCartao}")
    public ResponseEntity<Void> criarBiometria(@PathVariable Long idCartao, @Valid BiometriaRequest request, UriComponentsBuilder builder){
        logger.info("method=criarBiometria, msg=criando biometria");

        Optional<Cartao> possivelCartao = cartaoRepository.findById(idCartao);
        if(possivelCartao.isPresent()) {
            Cartao cartao = possivelCartao.get();
            Biometria biometria = request.paraBiometria(cartao);
            biometriaRepository.save(biometria);

            URI uri = builder
                    .path("/api/v1/biometrias/{id}")
                    .buildAndExpand(biometria.getId())
                    .toUri();

            logger.info("method=criarBiometria, msg=biometria {} cadastrada com sucesso", request.getFingerprint());
            return ResponseEntity.created(uri).build();
        }

        logger.error("method=criarBiometria, msg=cartão de id {} não encontrado", idCartao);
        return ResponseEntity.notFound().build();
    }
}
