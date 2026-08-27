package com.api.tuctapi.repository;

import com.api.tuctapi.model.Gira;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public  interface GiraRepository  extends JpaRepository<Gira, Integer> {
    List<Gira> findByIsPublicTrue();

    List<Gira> findByIsPublicTrueAndDateGiraBetween(
            LocalDateTime inicio,
            LocalDateTime fim
    );

    List<Gira> findByDateGiraBetween(
            LocalDateTime start,
            LocalDateTime end
    );

}