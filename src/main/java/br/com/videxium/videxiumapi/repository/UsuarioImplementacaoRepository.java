package br.com.videxium.videxiumapi.repository;

import br.com.videxium.videxiumapi.entity.UsuarioEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UsuarioImplementacaoRepository {

    private final EntityManager entityManager;

    public UsuarioImplementacaoRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<UsuarioEntity> recuperarUsuario(String usuario) {
        String query = """
                SELECT usuarioEntity
                FROM UsuarioEntity usuarioEntity
                WHERE usuarioEntity.usuario = :usuarioParameter
        """;
        TypedQuery<UsuarioEntity> typedQuery = this.entityManager.createQuery(query, UsuarioEntity.class);
            typedQuery.setParameter("usuarioParameter", usuario);
        return typedQuery.getResultList().stream().findFirst();
    }

    public Optional<UsuarioEntity> recuperarHashCadastro(String hashCadastro) {
        String query = """
                SELECT usuarioEntity
                FROM UsuarioEntity usuarioEntity
                WHERE usuarioEntity.hashCadastro = :hashCadastroParameter
        """;
        TypedQuery<UsuarioEntity> typedQuery = this.entityManager.createQuery(query, UsuarioEntity.class);
        typedQuery.setParameter("hashCadastroParameter", hashCadastro);
        return typedQuery.getResultList().stream().findFirst();
    }

}
