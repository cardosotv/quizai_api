
package com.cardosotv.quizai.model.repositories;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.cardosotv.quizai.model.entities.Question;

public interface QuestionRepository extends JpaRepository<Question, UUID>{

    @Query("SELECT q FROM Question q WHERE q.deletedDate is null")
    public List<Question> findAllActivatedQuestions(); 
}
