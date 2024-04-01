package com.cardosotv.quizai.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cardosotv.quizai.model.entities.GameQuestions;

public interface GameQuestionsRepository extends JpaRepository<GameQuestions, UUID>  {

}
