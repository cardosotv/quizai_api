package com.cardosotv.quizai.model.DTO;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author Tiago Cardoso on 04-03-2024
 */
public class GameDTO {


    private UUID id;
    private int score;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date startedDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date finishedDate;
    private List<GameQuestionsDTO> gameQuestions = new ArrayList<GameQuestionsDTO>();
    private UserDTO user;



    public GameDTO(UUID id, int score, Date startedDate, Date finishedDate
                , List<GameQuestionsDTO> gameQuestions, UserDTO user) {
        this.id = id;
        this.score = score;
        this.startedDate = startedDate;
        this.finishedDate = finishedDate;
        this.gameQuestions = gameQuestions;
        this.user = user;
    }

    public GameDTO() {}

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
     * @return List<GameQuestionsDTO> return the questions
     */
    public List<GameQuestionsDTO> getGameQuestions() {
        return gameQuestions;
    }

    /**
     * @param questions the questions to set
     */
    public void setGameQuestions(List<GameQuestionsDTO> questions) {
        this.gameQuestions = questions;
    }

    /**
     * @return User return the user
     */
    public UserDTO getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(UserDTO user) {
        this.user = user;
    }

}
