package br.com.videxium.videxiumapi.service;

import java.time.Instant;
import java.util.Arrays;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.videxium.videxiumapi.entity.UsuarioEntity;
import br.com.videxium.videxiumapi.enumeration.PerfilUsuarioEnumeration;
import br.com.videxium.videxiumapi.exception.RegraNegocialException;
import br.com.videxium.videxiumapi.exception.ResourceAlreadyExistsException;
import br.com.videxium.videxiumapi.exception.ResourceNotFoundException;
import br.com.videxium.videxiumapi.repository.UsuarioImplementacaoRepository;
import br.com.videxium.videxiumapi.repository.UsuarioRepository;
import br.com.videxium.videxiumapi.transfer.PageResponseTransfer;
import br.com.videxium.videxiumapi.transfer.UsuarioAtualizarRequestTransfer;
import br.com.videxium.videxiumapi.transfer.UsuarioResponseTransfer;
import br.com.videxium.videxiumapi.util.JwtUtil;
import br.com.videxium.videxiumapi.util.PaginationUtil;

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
    	
    	this.validarPerfilUsuario(usuarioEntity.getPerfil());

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
    
    private void validarPerfilUsuario(String perfil) {
    	if (Arrays.stream(PerfilUsuarioEnumeration.values())
    			.noneMatch(perfilUsuario -> perfilUsuario.name().equalsIgnoreCase(perfil))) {
    		throw new RegraNegocialException("Usuário sem privilégios suficiente para realizar essa operação!");
    	}
    }
    
    public UsuarioEntity atualizar(UsuarioAtualizarRequestTransfer usuarioAtualizarRequestTransfer) {
    	
    	UsuarioEntity usuarioCadastrado = this.usuarioRepository
    			.findById(usuarioAtualizarRequestTransfer.getCode())
                .orElseThrow(() -> {
                    throw new ResourceNotFoundException("Falha ao tentar recuperar o usuário!");
                });
    	
    		usuarioCadastrado.setNome(usuarioAtualizarRequestTransfer.getNome());
    		usuarioCadastrado.setUsuario(usuarioAtualizarRequestTransfer.getUsuario());
    		usuarioCadastrado.setUpdatedAt(Instant.now());
    	
    	return this.usuarioRepository.save(usuarioCadastrado);
    }

    public PageResponseTransfer<UsuarioResponseTransfer> recuperarTodos(int page, int size, String search) {

        Pageable pageable = PaginationUtil.createPageRequest(page, size, "code");

        Page<UsuarioEntity> usuarioEntityPage;

        if (search != null && !search.trim().isEmpty()) {
            usuarioEntityPage = usuarioRepository.recuperarTodosPor(search, pageable);
        } else {
            usuarioEntityPage = usuarioRepository.findAll(pageable);
        }

        return PaginationUtil.toPageResponse(usuarioEntityPage, UsuarioResponseTransfer::fromEntity);
    }

}
