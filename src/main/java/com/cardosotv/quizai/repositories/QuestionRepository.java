
package com.cardosotv.quizai.repositories;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cardosotv.quizai.model.DTO.OptionDTO;
import com.cardosotv.quizai.model.entities.Option;
import com.cardosotv.quizai.model.entities.Question;

public interface QuestionRepository extends JpaRepository<Question, UUID>{

    @Query("SELECT q FROM Question q WHERE q.deletedDate is null")
    public List<Question> findAllActivatedQuestions(); 


    //@Query(value="SELECT q.* FROM questions q WHERE q.id_subject = ?1"
    @Query(value="select q.* \n" + //
            "\t   from questions q\n" + //
            "\t   where q.deleted_date is null \n" + //
            "\t\t   and q.id_subject = ?2 \n" + //
            "\t   \t and q.id not in (\n" + //
            "\t\tselect distinct gq.question_id\n" + //
            "\t\t  from game_questions gq \n" + //
            "\tinner join games g on g.id = gq.id_game \n" + //
            "\t\t where  gq.is_correct = true and\n" + //
            "\t\t\t\tg.user_id = ?1) \n" + //
            "\t order by random() limit 10"
        , nativeQuery = true)
    public List<Question> findQuestionsNotAnsweredByUser(UUID userID, UUID subjectID);

}
