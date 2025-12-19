package br.com.videxium.videxiumapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.videxium.videxiumapi.entity.UsuarioEntity;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    @Query("""
        SELECT usuario
        FROM UsuarioEntity usuario
        WHERE 
            UPPER(usuario.nome) LIKE UPPER(CONCAT('%', :termoPesquisa, '%'))
        OR
            UPPER(usuario.usuario) LIKE UPPER(CONCAT('%', :termoPesquisa, '%'))
    """)
    Page<UsuarioEntity> recuperarTodosPor(@Param("termoPesquisa") String termoPesquisa, Pageable pageable);

}