package br.com.videxium.videxiumapi.entity;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_usuario")
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code", unique = true, nullable = false)
    private Long code;

    @Column(name = "code_public", unique = true, nullable = false)
    private String codePublic = UUID.randomUUID().toString();

    @Column(name = "perfil", nullable = false)
    private String perfil;

    @Column(name = "nome", unique = true, nullable = false)
    private String nome;

    @Column(name = "usuario", unique = true, nullable = false)
    private String usuario;

    @Column(name = "senha", nullable = false)
    private String senha;

    @Column(name = "token", unique = true, nullable = false)
    private String token;

    @Column(name = "hash_cadastro", unique = true)
    private String hashCadastro;

    @Column(name = "is_conta_verificada")
    private Boolean isContaVerificada = false;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @Column(name = "active", nullable = false)
    private Boolean active = true;

    public UsuarioEntity() {}

    public UsuarioEntity(Long code) {
		this.code = code;
	}

	public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getCodePublic() {
        return codePublic;
    }

    public void setCodePublic(String codePublic) {
        this.codePublic = codePublic;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getIsContaVerificada() {
        return isContaVerificada;
    }

    public void setIsContaVerificada(Boolean contaVerificada) {
        isContaVerificada = contaVerificada;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getHashCadastro() {
        return hashCadastro;
    }

    public void setHashCadastro(String hashCadastro) {
        this.hashCadastro = hashCadastro;
    }

    public Boolean getContaVerificada() {
        return isContaVerificada;
    }

    public void setContaVerificada(Boolean contaVerificada) {
        isContaVerificada = contaVerificada;
    }
}
