package br.com.zupacademy.msproposta.novacarteira;

import br.com.zupacademy.msproposta.associacartao.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarteiraRepository extends JpaRepository<Carteira, Long> {

    boolean existsByCartaoAndTipo(Cartao cartao, TipoCarteira tipoCarteira);

}
