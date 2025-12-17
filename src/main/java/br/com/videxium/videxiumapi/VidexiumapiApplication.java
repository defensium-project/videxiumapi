package br.com.videxium.videxiumapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;

@SpringBootApplication
@RestController
@RequestMapping({ "", "/", "/api" })
public class VidexiumapiApplication {

	private static final Logger log = LoggerFactory.getLogger(VidexiumapiApplication.class);

	@Value("${app.version}")
	private String versao;

	@Autowired
	private Environment environment;

	private final LocalDateTime dataHoraImplantacao = LocalDateTime.now();

	public static void main(String[] args) {
		SpringApplication.run(VidexiumapiApplication.class, args);
	}

	@GetMapping
	public LinkedHashMap<String, String> getInformation(HttpServletRequest httpServletRequest) throws UnknownHostException {

		LinkedHashMap<String, String> informacao = new LinkedHashMap<>();
		informacao.put("Aplicação", "VidexiumService");
		informacao.put("Porta", environment.getProperty("local.server.port"));
		informacao.put("Descrição", "Sistema Gerenciador de Videos Online");
		informacao.put("Ambiente", "Desenvolvimento");
		informacao.put("Implantação", getRecuperarDataHora());
		informacao.put("Versão", versao);
		informacao.put("Endereço", InetAddress.getLocalHost().getHostAddress());
		informacao.put("Demanda", getDemanda());
		informacao.put("URL", httpServletRequest.getRequestURL().toString());

		log.warn("{}", imprimirLog(informacao));

		return informacao;
	}

	private String getRecuperarDataHora() {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		return this.dataHoraImplantacao.format(dateTimeFormatter);
	}

	private String getDemanda() {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
		return "VIDEXIUM".concat(LocalDateTime.now().format(dateTimeFormatter)).concat("API");
	}

	private String imprimirLog(Object object) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
		} catch (JsonProcessingException e) {
			return "[Erro ao converter objeto para JSON]";
		}
	}

}
