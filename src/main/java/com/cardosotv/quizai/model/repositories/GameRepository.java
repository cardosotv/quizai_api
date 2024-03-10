package com.cardosotv.quizai.model.repositories;


import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cardosotv.quizai.model.entities.Game;

public interface GameRepository extends JpaRepository<Game, UUID>{

    //https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html
    public Page<Game> findByUserId(UUID userId, PageRequest pageRequest);

    // https://docs.spring.io/spring-data/jpa/reference/repositories/query-methods-details.html#repositories.limit-query-result 
    //@Query("SELECT g FROM Game g WHERE order by g.score desc")
    public Page<Game> findTop10ByOrderByScoreDesc(PageRequest pageRequest);
    //public Page<Game> (PageRequest pageRequest);
}

