package com.cardosotv.quizai.model.entities;

import java.util.Date;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name="options")
public class Option {

    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private UUID id;
    @NotBlank  
    @Column(nullable = false)
    private String option; 
    @Column(name = "is_correct", nullable = false)
    private Boolean isCorrect;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_question")
    private Question question;


    public Option(@NotBlank String option, Boolean isCorrect, Question question) {
        this.option = option;
        this.isCorrect = isCorrect;
        this.question = question;
    }

    // Default constructr necessary for JPA Parent-Child transaction
    public Option(){}

    /**
     * @return String return the option
     */
    public String getOption() {
        return option;
    }

    /**
     * @param option the option to set
     */
    public void setOption(String option) {
        this.option = option;
    }

    /**
     * @return Boolean return the isCorrect
     */
    public Boolean isIsCorrect() {
        return isCorrect;
    }

    /**
     * @param isCorrect the isCorrect to set
     */
    public void setIsCorrect(Boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    /**
     * @return Question return the question
     */
    public Question getQuestion() {
        return question;
    }

    /**
     * @param question the question to set
     */
    public void setQuestion(Question question) {
        this.question = question;
    }

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

}
