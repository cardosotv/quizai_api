package com.cardosotv.quizai.model.entities;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="games")
public class Game extends Default{

    private int score;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    @Column(name = "started_date", nullable = false)
    private Date startedDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    @Column(name = "finished_date")
    private Date finishedDate;
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User user;
    private UUID idSubject;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "game", cascade = CascadeType.ALL)
    private List<GameQuestions> questions = new ArrayList<GameQuestions>();

    
    public Game(java.util.Date createdDate, UUID createdBy, int score, Date startedDate, Date finishedDate
            , User user, UUID idSubject, List<GameQuestions> questions) {
        super(createdDate, createdBy);
        this.score = score;
        this.startedDate = startedDate;
        this.finishedDate = finishedDate;
        this.user = user;
        this.idSubject = idSubject;
        this.questions = questions;
    }

    public Game(){}

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
     * @return Date return the startedDate
     */
    public Date getStartedDate() {
        return startedDate;
    }

    /**
     * @param startedDate the startedDate to set
     */
    public void setStartedDate(Date startedDate) {
        this.startedDate = startedDate;
    }

    /**
     * @return Date return the finishedDate
     */
    public Date getFinishedDate() {
        return finishedDate;
    }

    /**
     * @param finishedDate the finishedDate to set
     */
    public void setFinishedDate(Date finishedDate) {
        this.finishedDate = finishedDate;
    }

    /**
     * @return User return the user
     */
    public User getUser() {
        return user;
    }

    // /**
    //  * @param user the user to set
    //  */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return Subject return the subject
     */
    public UUID getIdSubject() {
        return this.idSubject;
    }

    /**
     * @param subject the subject to set
     */
    public void setSubject(UUID idSubject) {
        this.idSubject = idSubject;
    }

    /**
     * @return List<GameQuestions> return the questions
     */
    public List<GameQuestions> getQuestions() {
        return questions;
    }

    // /**
    //  * @param questions the questions to set
    //  */
    public void setQuestions(List<GameQuestions> questions) {
        this.questions = questions;
    }

}
