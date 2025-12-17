package br.com.videxium.videxiumapi.service;

import br.com.videxium.videxiumapi.entity.UsuarioEntity;
import br.com.videxium.videxiumapi.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioEntity cadastrar(UsuarioEntity usuarioEntity) {
        return this.usuarioRepository.save(usuarioEntity);
    }

}
