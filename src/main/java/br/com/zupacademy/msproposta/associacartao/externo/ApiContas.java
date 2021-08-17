package br.com.zupacademy.msproposta.associacartao.externo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "contas-api", url = "${analise.conta.host}")
public interface ApiContas {

    @GetMapping("${analise.conta.endpoint}")
    SolicitacaoCartaoResponse gerarCartao(@RequestParam String idProposta);
}
