package br.com.videxium.videxiumapi.transfer;

import jakarta.validation.constraints.NotBlank;

public class UsuarioAtualizarRequestTransfer {

    private Long code;

    @NotBlank(message = "O campo 'nome' é de preenchimento obrigatório")
    private String nome;

    @NotBlank(message = "O campo 'usuario' é de preenchimento obrigatório")
    private String usuario;

    public UsuarioAtualizarRequestTransfer() {}

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
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

}
