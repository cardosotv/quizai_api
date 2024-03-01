package com.cardosotv.quizai.model.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="questions")
public class Question extends Default{

    // @Id
    // @GeneratedValue(strategy =  GenerationType.AUTO)
    // private UUID id;
    @NotBlank
    @Column(nullable =  false)
    private String question;
    @Column(name="id_subject", nullable = false)
    private UUID idSubject;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "question", cascade = CascadeType.ALL)
    private List<Option> options = new ArrayList<Option>();


    public Question(Date createdDate, UUID createdBy, @NotBlank String question, 
    //public Question(@NotBlank String question, 
            @NotBlank UUID idSubject, List<Option> options) {

        super(createdDate, createdBy);
        this.question = question;
        this.idSubject = idSubject;
        this.options = options;
    }

    // Default constructor      
    public Question(){}


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
     * @return Subject return the subject
     */
    public UUID getSubject() {
        return idSubject;
    }

    /**
     * @param subject the subject to set
     */
    public void setSubject(UUID idSubject) {
        this.idSubject = idSubject;
    }

    /**
     * @return List<Option> return the options
     */
    public List<Option> getOptions() {
        return options;
    }

    /**
     * @param options the options to set
     */
    public void setOptions(List<Option> options) {
        this.options = options;
    }


    // /**
    //  * @return UUID return the id
    //  */
    // public UUID getId() {
    //     return id;
    // }

    // /**
    //  * @param id the id to set
    //  */
    // public void setId(UUID id) {
    //     this.id = id;
    // }

    /**
     * @return UUID return the idSubject
     */
    public UUID getIdSubject() {
        return idSubject;
    }

    /**
     * @param idSubject the idSubject to set
     */
    public void setIdSubject(UUID idSubject) {
        this.idSubject = idSubject;
    }

    // Responsable for copy an object from client request which would want update up on database
    public void updateObject(Question question){
        this.setId(question.getId()); 
        this.question = question.getQuestion();
        this.options = question.getOptions();
    }
}
