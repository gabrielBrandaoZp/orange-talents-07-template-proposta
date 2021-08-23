package br.com.zupacademy.msproposta.novaproposta;

import br.com.zupacademy.msproposta.associacartao.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PropostaRepository extends JpaRepository<Proposta, Long> {

    Optional<Proposta> findByDocumento(String documento);

    boolean existsByCartaoAndEmail(Cartao cartao, String email);
}
