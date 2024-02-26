package com.cardosotv.quizai.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ServerError extends RuntimeException{

    private Object object;

    // implements constructor 
    public ServerError(String message, Object object) {
        super(message);
        this.object = object;
    }

    //implements getter
    public Object getObject() {
        return this.object;
    }

}
