package com.cardosotv.quizai.model.DTO;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;

import com.cardosotv.quizai.model.entities.Option;
import com.cardosotv.quizai.model.entities.Question;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;
import lombok.Builder;

/**
 * @author Tiago Cardoso on 01-03-2024
 * https://towardsdev.com/data-transfer-object-dto-in-spring-boot-c00678cc5946
 */
@SuppressWarnings("deprecation")
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties({"hibernate_lazy_initializer", "handler"})
public class QuestionDTO {

    private UUID id;
    private String question;
    private List<OptionDTO> options;

    public QuestionDTO(UUID id, String question, List<OptionDTO> options) {
        this.id = id;
        this.question = question;
        this.options = options;
    }


    public QuestionDTO() {}


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
     * @return String return the question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * @param question the question to set
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * @return List<OptionDTO> return the options
     */
    public List<OptionDTO> getOptions() {
        return options;
    }

    /**
     * @param options the options to set
     */
    public void setOptions(List<OptionDTO> options) {
        this.options = options;
    }

}
