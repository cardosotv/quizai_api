package com.cardosotv.quizai.model.entities;

public class Authorization {

    private String token;

    public Authorization(String token) {
        this.token = token;
    }

    /**
     * @return String return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token the token to set
     */
    public void setToken(String token) {
        this.token = token;
    }

}
