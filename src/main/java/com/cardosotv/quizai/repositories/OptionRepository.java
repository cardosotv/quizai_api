package com.cardosotv.quizai.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cardosotv.quizai.model.entities.Option;

public interface OptionRepository extends JpaRepository<Option, UUID> {

    @Query(value="SELECT q.* FROM options q WHERE q.id_question = ?1 and is_correct = true"
        , nativeQuery = true)
    public Option findCorrectOptionByQuestion(UUID questionID);

}
