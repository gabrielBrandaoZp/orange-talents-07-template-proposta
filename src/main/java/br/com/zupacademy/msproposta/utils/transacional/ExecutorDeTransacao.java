package br.com.zupacademy.msproposta.utils.transacional;

import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class ExecutorDeTransacao{

    @Transactional
    public void executaNaTransacao(Runnable r) {
        r.run();
    }

}
