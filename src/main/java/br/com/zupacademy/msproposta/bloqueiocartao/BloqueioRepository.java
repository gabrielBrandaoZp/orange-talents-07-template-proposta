package br.com.zupacademy.msproposta.bloqueiocartao;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BloqueioRepository extends JpaRepository<Bloqueio, Long> {

    Optional<Bloqueio> findByCartaoId(Long id);
}
