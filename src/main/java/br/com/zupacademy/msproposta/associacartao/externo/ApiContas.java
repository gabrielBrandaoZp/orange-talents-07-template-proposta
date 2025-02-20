package br.com.zupacademy.msproposta.associacartao.externo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@FeignClient(name = "contas-api", url = "${analise.conta.host}")
public interface ApiContas {

    @GetMapping("${analise.conta.endpoint}")
    SolicitacaoCartaoResponse gerarCartao(@RequestParam String idProposta);

    @PostMapping("${analise.conta.bloqueia-cartao.endpoint}")
    BloqueioResponse bloquearCartao(@PathVariable String id, @RequestBody @Valid BloqueioRequest request);

    @PostMapping("${analise.conta.aviso-viagem.endpoint}")
    AvisoViagemResponseClient avisarViagemCartao(@PathVariable String id, @RequestBody @Valid AvisoViagemRequestClient request);

    @PostMapping("${analise.conta.cadastra-carteira.endpoint}")
    NovaCarteiraResponseClient cadastrarCarteira(@PathVariable String id, @RequestBody @Valid NovaCarteiraRequestClient request);
}
