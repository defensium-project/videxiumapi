package br.com.videxium.videxiumapi.service;

import br.com.videxium.videxiumapi.entity.UsuarioEntity;
import br.com.videxium.videxiumapi.exception.ResourceAlreadyExistsException;
import br.com.videxium.videxiumapi.repository.UsuarioImplementacaoRepository;
import br.com.videxium.videxiumapi.repository.UsuarioRepository;
import br.com.videxium.videxiumapi.util.JwtUtil;
import org.hibernate.validator.cfg.defs.UUIDDef;
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

        this.usuarioImplementacaoRepository.recuperarUsuario(usuarioEntity.getUsuario()).ifPresent(usuario -> {
            throw new ResourceAlreadyExistsException("Usuário já cadastrado na Base de Dados!");
        });

        usuarioEntity.setSenha(passwordEncoder.encode(usuarioEntity.getSenha()));
        usuarioEntity.setToken(jwtUtil.generateToken(usuarioEntity.getUsuario(), usuarioEntity.getPerfil()));
        usuarioEntity.setHashCadastro(jwtUtil.gerarHashToken(usuarioEntity.getToken()));

        this.usuarioRepository.save(usuarioEntity);

        enviarEmail(usuarioEntity);

        return usuarioEntity;
    }

    private void enviarEmail(UsuarioEntity usuarioEntity) {
        this.emailService.enviarEmail(usuarioEntity.getUsuario(), usuarioEntity.getHashCadastro());
    }

}
