package br.com.haroldo.qrcode.model.entity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
public class AutomaticCounter {

    private final AtomicInteger valor = new AtomicInteger(21000);

    public AutomaticCounter() {
        log.info("Contador de testes automatico inicializado com o valor " + valor);
    }

    @Scheduled(fixedRate = 2500)
    public void incrementar() {
        valor.incrementAndGet();
    }

    public int getValor() {
        return valor.get();
    }

}