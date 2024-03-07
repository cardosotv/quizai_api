package com.cardosotv.quizai.model.DTO;

import java.util.List;
import java.util.UUID;

import com.cardosotv.quizai.model.entities.Game;
import com.cardosotv.quizai.model.DTO.*;

/**
 * @author Tiago Cardoso on 04-03-2024
 */
public class GameQuestionsDTO {

    private UUID id; 
    private QuestionDTO question;
    private int time;
    private int score;
    private boolean isCorrect;
    private OptionDTO answer;


    public GameQuestionsDTO(UUID id, QuestionDTO question, int time,  int score
                                , boolean isCorrect, OptionDTO answer) {
        this.id = id;
        this.question = question;
        this.time = time;
        this.score = score;
        this.isCorrect = isCorrect;
        this.answer = answer;
    }

    public GameQuestionsDTO() {}


    /**
     * @return UUID return the id
     */
    public UUID getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * @return QuestionDTO return the questions
     */
    public QuestionDTO getQuestion() {
        return question;
    }

    /**
     * @param questions the questions to set
     */
    public void setQuestion(QuestionDTO question) {
        this.question = question;
    }

    /**
     * @return int return the time
     */
    public int getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(int time) {
        this.time = time;
    }

    /**
     * @return int return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * @param score the score to set
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * @return boolean return the isCorrect
     */
    public boolean isIsCorrect() {
        return isCorrect;
    }

    /**
     * @param isCorrect the isCorrect to set
     */
    public void setIsCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }


    /**
     * @return OptionDTO return the answer
     */
    public OptionDTO getAnswer() {
        return answer;
    }

    /**
     * @param answer the answer to set
     */
    public void setAnswer(OptionDTO answer) {
        this.answer = answer;
    }

}
