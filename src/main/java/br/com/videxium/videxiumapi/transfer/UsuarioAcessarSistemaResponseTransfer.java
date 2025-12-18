package br.com.videxium.videxiumapi.transfer;

public class UsuarioAcessarSistemaResponseTransfer {

    private String nome;

    private String usuario;

    private String token;

    public UsuarioAcessarSistemaResponseTransfer() {}

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
