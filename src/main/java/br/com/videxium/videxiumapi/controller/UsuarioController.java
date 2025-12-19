package br.com.videxium.videxiumapi.controller;

import br.com.videxium.videxiumapi.transfer.UsuarioAtualizarRequestTransfer;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.videxium.videxiumapi.entity.UsuarioEntity;
import br.com.videxium.videxiumapi.service.UsuarioService;

@RestController
@RequestMapping("/api/usuario")
@PreAuthorize("hasRole('ADMINISTRADOR')")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public UsuarioEntity cadastrar(@RequestBody UsuarioEntity usuarioEntity) {
        return this.usuarioService.cadastrar(usuarioEntity);
    }
    
    @PutMapping("/{code}")
    public ResponseEntity<UsuarioEntity> atualizar(
    		@RequestBody @Valid UsuarioAtualizarRequestTransfer usuarioAtualizarRequestTransfer,
    		@PathVariable Long code) {
        usuarioAtualizarRequestTransfer.setCode(code);
    	return ResponseEntity.ok(this.usuarioService.atualizar(usuarioAtualizarRequestTransfer));
    }

}
