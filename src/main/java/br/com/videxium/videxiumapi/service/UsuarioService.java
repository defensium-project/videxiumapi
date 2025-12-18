package br.com.videxium.videxiumapi.service;

import br.com.videxium.videxiumapi.entity.UsuarioEntity;
import br.com.videxium.videxiumapi.enumeration.PerfilUsuarioEnumeration;
import br.com.videxium.videxiumapi.repository.UsuarioImplementacaoRepository;
import br.com.videxium.videxiumapi.repository.UsuarioRepository;
import br.com.videxium.videxiumapi.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final UsuarioImplementacaoRepository usuarioImplementacaoRepository;

    private final EmailService emailService;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    public UsuarioService(
            UsuarioRepository usuarioRepository,
            UsuarioImplementacaoRepository usuarioImplementacaoRepository,
            EmailService emailService,
            PasswordEncoder passwordEncoder,
            JwtUtil jwtUtil) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioImplementacaoRepository = usuarioImplementacaoRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public UsuarioEntity cadastrar(UsuarioEntity usuarioEntity) {

//        if (this.usuarioImplementacaoRepository.isUsuarioCadastrado(usuarioEntity.getUsuario())) {}
            usuarioEntity.setSenha(passwordEncoder.encode(usuarioEntity.getSenha()));
            usuarioEntity.setPerfil(PerfilUsuarioEnumeration.ADMINISTRADOR.name());
            usuarioEntity.setToken(jwtUtil.generateToken(usuarioEntity.getUsuario(), usuarioEntity.getPerfil()));

            this.emailService.enviarEmail(usuarioEntity.getUsuario(), usuarioEntity.getToken());

        return this.usuarioRepository.save(usuarioEntity);
    }

}
