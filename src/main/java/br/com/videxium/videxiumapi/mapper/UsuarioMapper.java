package br.com.videxium.videxiumapi.mapper;

import br.com.videxium.videxiumapi.entity.UsuarioEntity;
import br.com.videxium.videxiumapi.transfer.UsuarioAtualizarRequestTransfer;

public class UsuarioMapper {

    public UsuarioEntity toEntity(UsuarioAtualizarRequestTransfer atualizarRequestTransfer) {
        UsuarioEntity usuarioEntity = new UsuarioEntity();
            usuarioEntity.setNome(atualizarRequestTransfer.getNome());
            usuarioEntity.setUsuario(atualizarRequestTransfer.getUsuario());
        return usuarioEntity;
    }

}
