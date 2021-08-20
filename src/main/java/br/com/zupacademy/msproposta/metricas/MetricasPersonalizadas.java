package br.com.zupacademy.msproposta.metricas;

import br.com.zupacademy.msproposta.novaproposta.PropostaRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class MetricasPersonalizadas {

    private final MeterRegistry meterRegistry;
    private final PropostaRepository propostaRepository;

    public MetricasPersonalizadas(MeterRegistry meterRegistry, PropostaRepository propostaRepository) {
        this.meterRegistry = meterRegistry;
        this.propostaRepository = propostaRepository;
    }

    public void contadorPropostasCriadas() {
        Collection<Tag> tags = new ArrayList<>();
        tags.add(Tag.of("emissora", "Mastercard"));
        tags.add(Tag.of("banco", "Itaú"));

        Counter contadorDePropostasCriadas = this.meterRegistry.counter("propostas_criadas", tags);
        contadorDePropostasCriadas.increment();
    }

    public void timerConsultarPropostas(Long propostaId) {
        Collection<Tag> tags = new ArrayList<>();
        tags.add(Tag.of("emissora", "Mastercard"));
        tags.add(Tag.of("banco", "Itaú"));

        Timer timerConsultarProposta = this.meterRegistry.timer("consultar_proposta", tags);
        timerConsultarProposta.record(() -> propostaRepository.findById(propostaId));
    }
}
