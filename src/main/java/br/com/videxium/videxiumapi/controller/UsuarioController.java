package br.com.videxium.videxiumapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.videxium.videxiumapi.entity.UsuarioEntity;
import br.com.videxium.videxiumapi.service.UsuarioService;
import br.com.videxium.videxiumapi.transfer.PageResponseTransfer;
import br.com.videxium.videxiumapi.transfer.UsuarioAtualizarRequestTransfer;
import br.com.videxium.videxiumapi.transfer.UsuarioResponseTransfer;
import jakarta.validation.Valid;

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

    @GetMapping
    public ResponseEntity<PageResponseTransfer<UsuarioResponseTransfer>> recuperarTodos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search) {
        return ResponseEntity.ok(usuarioService.recuperarTodos(page, size, search));
    }

}
