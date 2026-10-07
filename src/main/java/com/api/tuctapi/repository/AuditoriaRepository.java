package com.api.tuctapi.repository;

import com.api.tuctapi.model.Auditoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditoriaRepository
        extends JpaRepository<Auditoria, Integer> {
}