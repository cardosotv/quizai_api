package com.cardosotv.quizai.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(){}

    @Override
    public String getMessage(){
        return "Insufficient privileges to access the resource!";
    }

}
