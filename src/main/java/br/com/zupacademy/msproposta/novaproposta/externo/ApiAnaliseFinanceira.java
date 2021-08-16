package br.com.zupacademy.msproposta.novaproposta.externo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "analisefinanceira-api", url = "${analise.finaceira.host}")
public interface ApiAnaliseFinanceira {

    @PostMapping("/api/solicitacao")
    SolicitacaoAnaliseResponse verificaAnaliseFinanceira(@RequestBody SolicitacaoAnaliseRequest request);
}
