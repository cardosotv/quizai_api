package com.cardosotv.quizai.model.DTO;

import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;
import lombok.Data;

/**
 * @author Tiago Cardoso on 01-03-2024
 */
@SuppressWarnings("deprecation")
// @Data
// @Builder
// @JsonInclude(JsonInclude.Include.NON_NULL)
// @JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
// @JsonIgnoreProperties({"hibernate_lazy_initializer", "handler"})
public class OptionDTO {
    private UUID id;
    private String option;
    private Boolean isCorrect;

    public OptionDTO(UUID id, String option, Boolean isCorrect) {
        this.id = id;
        this.option = option;
        this.isCorrect = isCorrect;
    } 
    
    public OptionDTO() {}

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
     * https://towardsdev.com/data-transfer-object-dto-in-spring-boot-c00678cc5946
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

}
