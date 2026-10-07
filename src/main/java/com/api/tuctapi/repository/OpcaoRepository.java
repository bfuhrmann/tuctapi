package com.api.tuctapi.repository;

import com.api.tuctapi.model.Opcao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OpcaoRepository extends JpaRepository<Opcao, Integer> {

    List<Opcao> findByPerguntaId(Integer perguntaId);
}