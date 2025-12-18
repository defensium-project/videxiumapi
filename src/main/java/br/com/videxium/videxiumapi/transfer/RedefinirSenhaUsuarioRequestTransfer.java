package br.com.videxium.videxiumapi.transfer;

public class RedefinirSenhaUsuarioRequestTransfer {

    private String usuario;

    private String senhaAntiga;

    private String senhaNova;

    public RedefinirSenhaUsuarioRequestTransfer() {}

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenhaAntiga() {
        return senhaAntiga;
    }

    public void setSenhaAntiga(String senhaAntiga) {
        this.senhaAntiga = senhaAntiga;
    }

    public String getSenhaNova() {
        return senhaNova;
    }

    public void setSenhaNova(String senhaNova) {
        this.senhaNova = senhaNova;
    }

}
