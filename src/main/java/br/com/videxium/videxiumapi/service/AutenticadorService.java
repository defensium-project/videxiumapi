package br.com.videxium.videxiumapi.service;

import br.com.videxium.videxiumapi.entity.UsuarioEntity;
import br.com.videxium.videxiumapi.exception.AccountDeactivatedException;
import br.com.videxium.videxiumapi.exception.AccountNotVerifiedException;
import br.com.videxium.videxiumapi.exception.BadCredentialException;
import br.com.videxium.videxiumapi.exception.ResourceNotFoundException;
import br.com.videxium.videxiumapi.repository.UsuarioImplementacaoRepository;
import br.com.videxium.videxiumapi.repository.UsuarioRepository;
import br.com.videxium.videxiumapi.transfer.UsuarioAcessarSistemaRequestTransfer;
import br.com.videxium.videxiumapi.transfer.UsuarioAcessarSistemaResponseTransfer;
import br.com.videxium.videxiumapi.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class AutenticadorService {

    private final UsuarioImplementacaoRepository usuarioImplementacaoRepository;

    private final JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder;

    private final UsuarioRepository usuarioRepository;

    private final EmailService emailService;

    public AutenticadorService(
            UsuarioImplementacaoRepository usuarioImplementacaoRepository, JwtUtil jwtUtil,
            PasswordEncoder passwordEncoder, UsuarioRepository usuarioRepository, EmailService emailService) {
        this.usuarioImplementacaoRepository = usuarioImplementacaoRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.usuarioRepository = usuarioRepository;
        this.emailService = emailService;
    }

    public UsuarioAcessarSistemaResponseTransfer acessarSistema(UsuarioAcessarSistemaRequestTransfer usuarioAcessarSistemaRequestTransfer) {

        Optional<UsuarioEntity> usuarioEntityOptional = Optional.ofNullable(this.usuarioImplementacaoRepository
                .recuperarUsuario(usuarioAcessarSistemaRequestTransfer.getUsuario())
                .filter(usuario -> passwordEncoder.matches(usuarioAcessarSistemaRequestTransfer.getSenha(), usuario.getSenha()))
                .orElseThrow(() -> new BadCredentialException("Os dados informados são inválidos!")));

        if (!usuarioEntityOptional.get().getActive()) {
            throw new AccountDeactivatedException("A conta informada está inativa!");
        }

        if (!usuarioEntityOptional.get().getIsContaVerificada()) {
            throw new AccountNotVerifiedException("A conta informada não foi verificada!");
        }

        UsuarioAcessarSistemaResponseTransfer usuarioAcessarSistemaResponseTransfer = new UsuarioAcessarSistemaResponseTransfer();
            usuarioAcessarSistemaResponseTransfer.setUsuario(usuarioEntityOptional.get().getUsuario());
            usuarioAcessarSistemaResponseTransfer.setNome(usuarioEntityOptional.get().getNome());
            usuarioAcessarSistemaResponseTransfer.setToken(usuarioEntityOptional.get().getToken());
        return usuarioAcessarSistemaResponseTransfer;
    }

    public Map<String, Object> verificarEmail(String hashCadastro) {
        UsuarioEntity usuarioEntity = this.usuarioImplementacaoRepository
            .recuperarHashCadastro(hashCadastro)
            .orElseThrow(() -> {
                throw new ResourceNotFoundException("Não foi possível verificar o e-mail!");
            });
        usuarioEntity.setIsContaVerificada(true);
        this.usuarioRepository.save(usuarioEntity);

        return Map.of("mensagem", "E-mail Verificado com Sucesso!");
    }

    public Map<String, Object> reenviarEmail(UsuarioAcessarSistemaRequestTransfer usuarioAcessarSistemaRequestTransfer) {

        UsuarioEntity usuarioEntity = this.usuarioImplementacaoRepository
                .recuperarUsuario(usuarioAcessarSistemaRequestTransfer.getUsuario())
                .orElseThrow(() -> {
                    throw new ResourceNotFoundException("Não foi possível reenviar o e-mail!");
                });

        usuarioEntity.setIsContaVerificada(false);
        usuarioEntity.setToken(jwtUtil.generateToken(usuarioEntity.getUsuario(), usuarioEntity.getPerfil()));
        usuarioEntity.setHashCadastro(jwtUtil.gerarHashToken(usuarioEntity.getToken()));

        this.usuarioRepository.save(usuarioEntity);

        this.emailService.enviarEmail(usuarioEntity.getUsuario(), usuarioEntity.getHashCadastro());

        return Map.of("mensagem", "E-mail Verificado com Sucesso!");
    }


}
