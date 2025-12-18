package br.com.videxium.videxiumapi.controller;

import br.com.videxium.videxiumapi.service.AutenticadorService;
import br.com.videxium.videxiumapi.transfer.RedefinirSenhaUsuarioRequestTransfer;
import br.com.videxium.videxiumapi.transfer.UsuarioAcessarSistemaRequestTransfer;
import br.com.videxium.videxiumapi.transfer.UsuarioAcessarSistemaResponseTransfer;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    @GetMapping("/verificar-conta")
    public ResponseEntity<Map<String, Object>> verificarEmail(@RequestParam String token) {
        return ResponseEntity.ok(this.autenticadorService.verificarEmail(token));
    }

    @PostMapping("/reenviar-email")
    public ResponseEntity<Map<String, Object>> reenviarEmail(@RequestBody UsuarioAcessarSistemaRequestTransfer usuarioAcessarSistemaRequestTransfer) {
        return ResponseEntity.ok().body(autenticadorService.reenviarEmail(usuarioAcessarSistemaRequestTransfer));
    }

    @PostMapping("/redefinir-senha")
    public ResponseEntity<Map<String, Object>> redefinirSenhaUsuario(
            @RequestBody RedefinirSenhaUsuarioRequestTransfer redefinirSenhaUsuarioRequestTransfer,
            Authentication authentication) {
        redefinirSenhaUsuarioRequestTransfer.setUsuario(authentication.getName());
        return ResponseEntity.ok().body(autenticadorService.redefinirSenhaUsuario(redefinirSenhaUsuarioRequestTransfer));
    }

}
