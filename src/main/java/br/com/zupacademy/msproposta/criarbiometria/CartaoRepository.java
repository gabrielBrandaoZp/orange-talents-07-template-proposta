package br.com.zupacademy.msproposta.criarbiometria;

import br.com.zupacademy.msproposta.associacartao.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartaoRepository extends JpaRepository<Cartao, Long> {
}
