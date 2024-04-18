package com.cardosotv.quizai.model.DTO;

import java.util.UUID;

/**
 * @author Tiago Cardoso on 04-03-2024
 * at 18-04-2024
 * purpose: Data Transfor object to handle with the request to 
 *          update the questions with user answer.
 */
public class AnswerDTO {

    private UUID gameID; 
    private UUID guestionID;
    private int answerTime;
    private UUID optionAnswered;
    
    public AnswerDTO(UUID gameID, UUID guestionID, int answerTime, UUID optionAnswered) {
        this.gameID = gameID;
        this.guestionID = guestionID;
        this.answerTime = answerTime;
        this.optionAnswered = optionAnswered;
    }

    /**
     * @return UUID return the gameID
     */
    public UUID getGameID() {
        return gameID;
    }

    /**
     * @param gameID the gameID to set
     */
    public void setGameID(UUID gameID) {
        this.gameID = gameID;
    }

    /**
     * @return UUID return the guestionID
     */
    public UUID getGuestionID() {
        return guestionID;
    }

    /**
     * @param guestionID the guestionID to set
     */
    public void setGuestionID(UUID guestionID) {
        this.guestionID = guestionID;
    }

    /**
     * @return int return the answerTime
     */
    public int getAnswerTime() {
        return answerTime;
    }

    /**
     * @param answerTime the answerTime to set
     */
    public void setAnswerTime(int answerTime) {
        this.answerTime = answerTime;
    }

    /**
     * @return UUID return the optionAnswered
     */
    public UUID getOptionAnswered() {
        return optionAnswered;
    }

    /**
     * @param optionAnswered the optionAnswered to set
     */
    public void setOptionAnswered(UUID optionAnswered) {
        this.optionAnswered = optionAnswered;
    }

}
