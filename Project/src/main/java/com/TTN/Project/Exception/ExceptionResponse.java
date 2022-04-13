package com.TTN.Project.Exception;

import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.List;

public class ExceptionResponse {
    private Date timestamp;
    private HttpStatus status;
    private String message;
    private List errors;

    public ExceptionResponse(Date timestamp, String message, HttpStatus status, List errors) {
        this.timestamp = timestamp;
        this.message = message;
        this.status = status;
        this.errors = errors;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public List getErrors() {
        return errors;
    }

    public void setErrors(List errors) {
        this.errors = errors;
    }

}
