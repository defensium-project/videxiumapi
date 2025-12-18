package br.com.videxium.videxiumapi.transfer;

public class UsuarioAcessarSistemaRequestTransfer {

    private String usuario;

    private String senha;

    public UsuarioAcessarSistemaRequestTransfer() {}

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

}
