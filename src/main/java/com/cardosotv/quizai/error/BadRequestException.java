package com.cardosotv.quizai.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

    private Object object;

    public BadRequestException(String message, Object object){
        super(message);
        this.object = object;
    }

    public Object getObject() {
        return object;
    }
}
