package br.com.zupacademy.msproposta.criarbiometria;

import br.com.zupacademy.msproposta.associacartao.Cartao;
import br.com.zupacademy.msproposta.associacartao.CartaoRepository;
import br.com.zupacademy.msproposta.utils.compartilhado.Metodos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class BiometriaController {
    private final Logger logger = LoggerFactory.getLogger(BiometriaController.class);

    private final CartaoRepository cartaoRepository;
    private final BiometriaRepository biometriaRepository;

    public BiometriaController(CartaoRepository cartaoRepository, BiometriaRepository biometriaRepository) {
        this.cartaoRepository = cartaoRepository;
        this.biometriaRepository = biometriaRepository;
    }

    @PostMapping("/cartoes/{idCartao}/biometrias")
    public ResponseEntity<Void> criarBiometria(@PathVariable Long idCartao, @Valid BiometriaRequest request, UriComponentsBuilder builder){
        logger.info("method=criarBiometria, msg=criando biometria");

        Optional<Cartao> possivelCartao = cartaoRepository.findById(idCartao);
        if(possivelCartao.isPresent()) {
            Cartao cartao = possivelCartao.get();
            Biometria biometria = request.paraBiometria(cartao);
            biometriaRepository.save(biometria);

            logger.info("method=criarBiometria, msg=biometria {} cadastrada com sucesso", request.getFingerprint());
            URI uri = Metodos.criarUri(builder, "/api/v1/biometrias/{id}", biometria.getId());
            return ResponseEntity.created(uri).build();
        }

        logger.error("method=criarBiometria, msg=cartão de id {} não encontrado", idCartao);
        return ResponseEntity.notFound().build();
    }
}
