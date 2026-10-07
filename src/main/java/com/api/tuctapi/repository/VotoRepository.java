package com.api.tuctapi.repository;

import com.api.tuctapi.model.Voto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VotoRepository extends JpaRepository<Voto, Integer> {

    boolean existsByPerguntaIdAndUsuarioId(
            Integer perguntaId,
            Integer usuarioId
    );

    boolean existsByPerguntaIdAndTokenAnonimo(
            Integer perguntaId,
            String tokenAnonimo
    );

    Optional<Voto> findByPerguntaIdAndUsuarioId(
            Integer perguntaId,
            Integer usuarioId
    );

    long countByPerguntaId(Integer perguntaId);

    long countByPerguntaIdAndOpcaoId(
            Integer perguntaId,
            Integer opcaoId
    );
    void deleteByPerguntaId(Integer perguntaId);
}