package br.com.zupacademy.msproposta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ServicoDePropostaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicoDePropostaApplication.class, args);
	}

}
