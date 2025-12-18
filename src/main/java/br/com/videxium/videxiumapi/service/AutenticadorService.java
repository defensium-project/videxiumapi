package br.com.videxium.videxiumapi.service;

import br.com.videxium.videxiumapi.entity.UsuarioEntity;
import br.com.videxium.videxiumapi.exception.AccountDeactivatedException;
import br.com.videxium.videxiumapi.exception.AccountNotVerifiedException;
import br.com.videxium.videxiumapi.exception.BadCredentialException;
import br.com.videxium.videxiumapi.repository.UsuarioImplementacaoRepository;
import br.com.videxium.videxiumapi.transfer.UsuarioAcessarSistemaRequestTransfer;
import br.com.videxium.videxiumapi.transfer.UsuarioAcessarSistemaResponseTransfer;
import br.com.videxium.videxiumapi.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AutenticadorService {

    private final UsuarioImplementacaoRepository usuarioImplementacaoRepository;

    private final JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder;

    public AutenticadorService(
            UsuarioImplementacaoRepository usuarioImplementacaoRepository,
            JwtUtil jwtUtil,
            PasswordEncoder passwordEncoder) {
        this.usuarioImplementacaoRepository = usuarioImplementacaoRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
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
            usuarioAcessarSistemaResponseTransfer.setToken(jwtUtil.generateToken(usuarioEntityOptional.get().getUsuario(), usuarioEntityOptional.get().getPerfil()));
        return usuarioAcessarSistemaResponseTransfer;
    }

}
