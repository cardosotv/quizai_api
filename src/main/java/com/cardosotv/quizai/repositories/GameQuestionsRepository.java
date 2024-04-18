package com.cardosotv.quizai.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cardosotv.quizai.model.entities.GameQuestions;

public interface GameQuestionsRepository extends JpaRepository<GameQuestions, UUID>  {

    @Query(value="SELECT q.* FROM game_questions q WHERE q.id_game = ?1 and q.question_id = ?2"
    , nativeQuery = true)
    public GameQuestions getByGameAndQuestion(UUID gameID, UUID questionID);



}
