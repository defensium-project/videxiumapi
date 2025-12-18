package br.com.videxium.videxiumapi.controller;

import br.com.videxium.videxiumapi.service.AutenticadorService;
import br.com.videxium.videxiumapi.transfer.UsuarioAcessarSistemaRequestTransfer;
import br.com.videxium.videxiumapi.transfer.UsuarioAcessarSistemaResponseTransfer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/autenticador")
public class AutenticadorController {

    private final AutenticadorService autenticadorService;

    public AutenticadorController(AutenticadorService autenticadorService) {
        this.autenticadorService = autenticadorService;
    }

    @PostMapping("/acessar")
    public UsuarioAcessarSistemaResponseTransfer acessarSistema(@RequestBody UsuarioAcessarSistemaRequestTransfer usuarioAcessarSistemaRequestTransfer) {
        return autenticadorService.acessarSistema(usuarioAcessarSistemaRequestTransfer);
    }

}
