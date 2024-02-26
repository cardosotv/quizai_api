package com.cardosotv.quizai.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException{


    private Object object;
    private String entityName;


    public NotFoundException(String entityName, Object object) {
        this.object = object;
        this.entityName = entityName;
    }

    public Object getObject(){
        return this.object;
    }

    @Override
    public String getMessage(){
        return String.format("%s not found!", this.entityName);
    }

}
