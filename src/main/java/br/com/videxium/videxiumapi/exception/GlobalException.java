package br.com.videxium.videxiumapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.Map;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(BadCredentialException.class)
    public ResponseEntity<Map<String, Object>> exception(BadCredentialException badCredentialException) {
        return buildResponse(HttpStatus.UNAUTHORIZED, badCredentialException.getMessage());
    }

    @ExceptionHandler(AccountDeactivatedException.class)
    public ResponseEntity<Map<String, Object>> exception(AccountDeactivatedException accountDeactivatedException) {
        return buildResponse(HttpStatus.FORBIDDEN, accountDeactivatedException.getMessage());
    }

    @ExceptionHandler(AccountNotVerifiedException.class)
    public ResponseEntity<Map<String, Object>> exception(AccountNotVerifiedException accountNotVerifiedException) {
        return buildResponse(HttpStatus.FORBIDDEN, accountNotVerifiedException.getMessage());
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> exception(ResourceAlreadyExistsException resourceAlreadyExistsException) {
        return buildResponse(HttpStatus.BAD_REQUEST, resourceAlreadyExistsException.getMessage());
    }

    private ResponseEntity<Map<String, Object>> buildResponse(HttpStatus httpStatus, String message) {
        Map<String, Object> objeto = Map.of("Data Requisição", Instant.now(), "Erro", message);
        return ResponseEntity.status(httpStatus).body(objeto);
    }

}
