package br.com.haroldo.qrcode.api;

import br.com.haroldo.qrcode.configs.SocialMediaConfig;
import br.com.haroldo.qrcode.api.dto.InstagramFollowersResponseDTO;
import br.com.haroldo.qrcode.model.entity.AutomaticCounter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tools.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("api/seguidores")
public class InstagramAPI {

    // Controle de horario
    private static final LocalTime INICIO_JORNADA = LocalTime.of(7, 0);
    private static final LocalTime FIM_JORNADA = LocalTime.of(23, 0);

    private final AutomaticCounter automaticCounter;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final SocialMediaConfig config;

    public InstagramAPI(AutomaticCounter automaticCounter, SocialMediaConfig config) {
        this.automaticCounter = automaticCounter;
        this.config = config;
    }

    //GET
    @GetMapping("/teste")
    public ResponseEntity obterQtdSequidoresTeste() {

        LocalDateTime agora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String dataHoraFormatada = agora.format(formatter);

        try {

            Thread.sleep(4000); // Delay de 5 segundos

            int contador = 0;

            if (horarioComercialDisponivel(agora, INICIO_JORNADA, FIM_JORNADA)) {
                log.error("Serviço disponível somente no horário entre " + INICIO_JORNADA + " e " + FIM_JORNADA + ".");
                return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(Map.of(
                        "error", "Serviço disponível somente no horário entre " + INICIO_JORNADA + " e " + FIM_JORNADA + ".",
                        "data", dataHoraFormatada
                    ));
            }

            log.info("obterQtdSeguidores Data e Hora: " + dataHoraFormatada);
            contador = automaticCounter.getValor();

            return ResponseEntity.ok(Map.of("totalSeguidores", contador));

        } catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of(
                    "error", "Serviço indisponível",
                    "data", dataHoraFormatada
                ));
        }

    }

    //GET
    @GetMapping("/contador")
    public ResponseEntity obterQtdSequidoresInstagram() throws InterruptedException {

        String BASE_URL_FACEBOOK = config.getBaseUrlFacebook();
        String INSTAGRAM_USER_ID = config.getInstagramUserId();
        String INSTAGRAM_ACCESS_TOKEN = config.getInstagramAccessToken();

        int quantidadeSeguidores = 0;

        LocalDateTime agora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        String dataHoraFormatada = agora.format(formatter);

        try {

            Thread.sleep(4000);

            String url = String.format(
                "%s/%s?fields=followers_count&access_token=%s",
                BASE_URL_FACEBOOK,
                INSTAGRAM_USER_ID,
                INSTAGRAM_ACCESS_TOKEN
            );

            log.info("obterQtdSequidoresInstagram Data e Hora: " + dataHoraFormatada);

            if (horarioComercialDisponivel(agora, INICIO_JORNADA, FIM_JORNADA)) {
                log.error("Serviço disponível somente no horário entre " + INICIO_JORNADA + " e " + FIM_JORNADA + ".");
                return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(Map.of(
                        "error", "Serviço disponível somente no horário entre " + INICIO_JORNADA + " e " + FIM_JORNADA + ".",
                        "data", dataHoraFormatada
                    ));
            }

            log.info("REQUEST PARA A API DO INSTAGRAM EXECUATADA: " + url + " horario : " + dataHoraFormatada);

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

            HttpResponse<String> response = HttpClient
                .newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                log.error("Erro na API Instagram: " + response.body());
                return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(
                        "error", "Erro na API Instagram: " + response.body(),
                        "data", dataHoraFormatada
                    ));
            }

            InstagramFollowersResponseDTO dto = objectMapper.readValue(response.body(), InstagramFollowersResponseDTO.class);

            quantidadeSeguidores = dto.getFollowersCount();

        } catch (Exception e) {
            log.error("Ocorreu um erro ao obter quantidade de seguidores", e.getMessage());
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                    "error", "Ocorreu um erro ao obter quantidade de seguidores: " + e.getMessage(),
                    "data", dataHoraFormatada
                ));
        }

        return ResponseEntity.ok(Map.of("totalSeguidores", quantidadeSeguidores));

    }

    private boolean horarioComercialDisponivel(LocalDateTime agora, LocalTime INICIO_JORNADA, LocalTime FIM_JORNADA) {
        LocalTime horaAtual = agora.toLocalTime();
        return horaAtual.isBefore(INICIO_JORNADA) || horaAtual.isAfter(FIM_JORNADA);
    }

}