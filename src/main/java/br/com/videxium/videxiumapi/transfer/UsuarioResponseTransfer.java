package br.com.videxium.videxiumapi.transfer;

import br.com.videxium.videxiumapi.entity.UsuarioEntity;

public class UsuarioResponseTransfer {

    private Long code;

    private String codePublic;

    private String perfil;

    private String nome;

    private String usuario;

    private String senha;

    private String token;

    private String hashCadastro;

    private String isContaVerificada;

    private String createdAt;

    private String updatedAt;

    private String deletedAt;

    private String active;

    public UsuarioResponseTransfer() {
    }

    public static UsuarioResponseTransfer fromEntity(UsuarioEntity usuarioEntity) {
        UsuarioResponseTransfer usuarioResponseTransfer = new UsuarioResponseTransfer();
            usuarioResponseTransfer.setCode(usuarioEntity.getCode());
            usuarioResponseTransfer.setCodePublic(usuarioEntity.getCodePublic());
            usuarioResponseTransfer.setPerfil(usuarioEntity.getPerfil());
            usuarioResponseTransfer.setNome(usuarioEntity.getNome());
            usuarioResponseTransfer.setUsuario(usuarioEntity.getUsuario());
            usuarioResponseTransfer.setHashCadastro(usuarioEntity.getHashCadastro());
            usuarioResponseTransfer.setIsContaVerificada(String.valueOf(usuarioEntity.getIsContaVerificada()));
            usuarioResponseTransfer.setCreatedAt(String.valueOf(usuarioEntity.getCreatedAt()));
            usuarioResponseTransfer.setUpdatedAt(String.valueOf(usuarioEntity.getUpdatedAt()));
            usuarioResponseTransfer.setDeletedAt(String.valueOf(usuarioEntity.getDeletedAt()));
            usuarioResponseTransfer.setActive(String.valueOf(usuarioEntity.getActive()));
        return usuarioResponseTransfer;
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

    public String getHashCadastro() {
        return hashCadastro;
    }

    public void setHashCadastro(String hashCadastro) {
        this.hashCadastro = hashCadastro;
    }

    public String getIsContaVerificada() {
        return isContaVerificada;
    }

    public void setIsContaVerificada(String isContaVerificada) {
        this.isContaVerificada = isContaVerificada;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

}
