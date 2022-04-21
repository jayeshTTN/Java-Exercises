package com.TTN.Project.Exception;

public class ResourceDoesNotExistException extends RuntimeException{
    public ResourceDoesNotExistException(String message) {
        super(message);
    }
}
