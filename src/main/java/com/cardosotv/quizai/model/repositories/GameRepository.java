package com.cardosotv.quizai.model.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cardosotv.quizai.model.entities.Game;

public interface GameRepository extends JpaRepository<Game, UUID>{

    //https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html
    public Page<Game> findByUserId(UUID userId, PageRequest pageRequest);
}
