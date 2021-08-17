package br.com.zupacademy.msproposta.novaproposta.externo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "analisefinanceira-api", url = "${analise.financeira.host}")
public interface ApiAnaliseFinanceira {

    @PostMapping("${analise.financeira.endpoint}")
    SolicitacaoAnaliseResponse verificaAnaliseFinanceira(@RequestBody SolicitacaoAnaliseRequest request);
}
