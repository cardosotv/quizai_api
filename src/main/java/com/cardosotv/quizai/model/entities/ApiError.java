package com.cardosotv.quizai.model.entities;

import org.springframework.http.HttpStatus;
import java.util.Date;


public class ApiError {

    private HttpStatus status;
    private Date datetime;
    private String message;
    private String details;
    private String path;
    private Object object;

    public ApiError(HttpStatus status, String message, String details, String path, Object object) {
        this.datetime = new Date();
        this.status = status;
        this.message = message;
        this.details = details;
        this.path = path;
        this.object = object;
    }

    /**
     * @return HttpStatus return the status
     */
    public HttpStatus getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    /**
     * @return Date return the datetime
     */
    public Date getDatetime() {
        return datetime;
    }

    /**
     * @return String return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return String return the details
     */
    public String getDetails() {
        return details;
    }

    /**
     * @param details the details to set
     */
    public void setDetails(String details) {
        this.details = details;
    }


    /**
     * @param datetime the datetime to set
     */
    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    /**
     * @return String return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }


    /**
     * @return Object return the object
     */
    public Object getObject() {
        return object;
    }

    /**
     * @param object the object to set
     */
    public void setObject(Object object) {
        this.object = object;
    }

}
