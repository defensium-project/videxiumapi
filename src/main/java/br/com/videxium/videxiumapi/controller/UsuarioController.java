package br.com.videxium.videxiumapi.controller;

import br.com.videxium.videxiumapi.entity.UsuarioEntity;
import br.com.videxium.videxiumapi.service.UsuarioService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public UsuarioEntity cadastrar(@RequestBody UsuarioEntity usuarioEntity) {
        return this.usuarioService.cadastrar(usuarioEntity);
    }

}
