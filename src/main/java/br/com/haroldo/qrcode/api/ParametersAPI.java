package br.com.haroldo.qrcode.api;

import br.com.haroldo.qrcode.model.entity.Parameters;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("api/parametros")
public class ParametersAPI {

    private final Parameters parameters;

    public ParametersAPI(Parameters parameters) {
        this.parameters = parameters;
    }

    //GET
    @GetMapping
    public ResponseEntity obterParametros() throws InterruptedException {

        log.info("obterParametros");

        Thread.sleep(2500); // Delay de 5 segundos

        return ResponseEntity.ok(Map.of("parametros", parameters));

    }

}