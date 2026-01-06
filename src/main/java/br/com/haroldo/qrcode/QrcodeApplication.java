package br.com.haroldo.qrcode;

import br.com.haroldo.qrcode.model.entity.Parameters;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@Slf4j
@SpringBootApplication
public class QrcodeApplication {

	public static void main(String[] args) {
		SpringApplication.run(QrcodeApplication.class, args);
	}

	@Bean
	public static Parameters criarParametros() {

		Parameters parameters = Parameters.builder()
			.titulo("Padaria Branca Flor - Seguidores")
			.tituloPagina("Padaria Branca Flor")
			.textoQrCode("https://www.instagram.com/padariabrancaflor/")
			.build();

		log.info("Parametros Iniciais");
		log.info("Titulo: " + parameters.getTitulo());
		log.info("Titulo Pagina: " + parameters.getTituloPagina());
		log.info("Texto QRCODE: " + parameters.getTextoQrCode());

		return parameters;
	}

}