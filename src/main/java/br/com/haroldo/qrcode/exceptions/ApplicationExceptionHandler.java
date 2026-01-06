package br.com.haroldo.qrcode.exceptions;

import br.com.haroldo.qrcode.api.dto.StandardErrorDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<?> handleNoResourceFoundException(NoResourceFoundException ex) {
        StandardErrorDTO erroPadrao = new StandardErrorDTO(HttpStatus.NOT_FOUND.value(), "Endpoint não encontrado");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erroPadrao);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        StandardErrorDTO erroPadrao = new StandardErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Erro ao processar a requisição");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erroPadrao);
    }

}