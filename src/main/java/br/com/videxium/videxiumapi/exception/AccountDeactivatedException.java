package br.com.videxium.videxiumapi.exception;

public class AccountDeactivatedException extends RuntimeException {

    public AccountDeactivatedException(String message) {
        super(message);
    }

}
