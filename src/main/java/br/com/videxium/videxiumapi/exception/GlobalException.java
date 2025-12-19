package br.com.videxium.videxiumapi.exception;

import java.time.Instant;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> exception(HttpMessageNotReadableException httpMessageNotReadableException) {
        return buildResponse(HttpStatus.BAD_GATEWAY, "A estrutura da requisição está errada!");
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> exception(ResourceNotFoundException resourceNotFoundException) {
        return buildResponse(HttpStatus.NOT_FOUND, resourceNotFoundException.getMessage());
    }
    
    @ExceptionHandler(RegraNegocialException.class)
    public ResponseEntity<Map<String, Object>> exception(RegraNegocialException regraNegocialException) {
        return buildResponse(HttpStatus.UNAUTHORIZED, regraNegocialException.getMessage());
    }
    
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Map<String, Object>> exception(HttpRequestMethodNotSupportedException httpRequestMethodNotSupportedException) {
        return buildResponse(HttpStatus.BAD_GATEWAY, "A requisição encaminhada não é suportada!");
    }
    
    private ResponseEntity<Map<String, Object>> buildResponse(HttpStatus httpStatus, String message) {
        Map<String, Object> objeto = Map.of("Data Requisição", Instant.now(), "Erro", message);
        return ResponseEntity.status(httpStatus).body(objeto);
    }

}
