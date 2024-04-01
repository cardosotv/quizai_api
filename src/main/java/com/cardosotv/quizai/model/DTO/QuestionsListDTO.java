package com.cardosotv.quizai.model.DTO;

import java.util.List;

public class QuestionsListDTO {

    private List<QuestionDTO> questions;

    public QuestionsListDTO(List<QuestionDTO> questions) {
        this.questions = questions;
    }

    public QuestionsListDTO() {}

    /**
     * @return List<QuestionDTO> return the questions
     */
    public List<QuestionDTO> getQuestions() {
        return questions;
    }

    /**
     * @param questions the questions to set
     */
    public void setQuestions(List<QuestionDTO> questions) {
        this.questions = questions;
    }

}
