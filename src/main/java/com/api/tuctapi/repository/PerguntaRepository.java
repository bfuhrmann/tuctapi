package com.api.tuctapi.repository;

import com.api.tuctapi.model.Pergunta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerguntaRepository extends JpaRepository<Pergunta, Integer> {
}