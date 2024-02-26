package com.cardosotv.quizai.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.cardosotv.quizai.model.entities.ApiError;

@ControllerAdvice
@RestControllerAdvice
public class RestExceptionHandler{

    // add the logger object context
    private static final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiError> handleNotFoundException(NotFoundException ex, 
                                                            WebRequest request){
                                                               
        ApiError error = new ApiError(HttpStatus.NOT_FOUND , ex.getMessage(),
            ex.getClass().getName(), request.getDescription(false),
            ex.getObject());
        
        // log the error to observability
        logger.error(ex.getClass().getName() + " : " + error.getPath() + " : " + ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(ServerError.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ApiError> handleServerException(ServerError ex, 
                                                            WebRequest request){
        
        ApiError error = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR , ex.getMessage(),
            ex.getClass().getSimpleName(), request.getDescription(false),
            ex.getObject());
        
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> handleBadRequestException(BadRequestException ex, 
                                                            WebRequest request){
        
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST , ex.getMessage(),
            ex.getClass().getSimpleName(), request.getDescription(false),
            ex.getObject());
        
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ApiError> handleUnauthorizedException(UnauthorizedException ex, 
                                                            WebRequest request){
        
        ApiError error = new ApiError(HttpStatus.UNAUTHORIZED , ex.getMessage(),
            ex.getClass().getSimpleName(), request.getDescription(false),
            null);
        
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

}
