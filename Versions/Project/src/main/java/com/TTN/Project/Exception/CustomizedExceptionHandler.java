package com.TTN.Project.Exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import javax.validation.ConstraintViolationException;
import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
@RestController
public class CustomizedExceptionHandler
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleALLException(Exception ex, WebRequest request){
        List<String> list = new ArrayList<String>(Arrays.asList(request.getDescription(false)));
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,list);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public final ResponseEntity<Object> userNotFoundException(Exception ex, WebRequest request){
        List<String> list = new ArrayList<String>(Arrays.asList(request.getDescription(false)));
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), HttpStatus.NOT_FOUND,list);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(CustomerAlreadyExistException.class)
    public final ResponseEntity<Object> customerAlreadyExist(CustomerAlreadyExistException ex, WebRequest request){
        List<String> list = new ArrayList<String>(Arrays.asList(ex.getMessage(),request.getDescription(false)));
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), "User already Exist", HttpStatus.BAD_REQUEST,list);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(PasswordMismatchException.class)
    public final ResponseEntity<Object> passwordMismatchException(PasswordMismatchException ex, WebRequest request){
        List<String> list = new ArrayList<String>(Arrays.asList(ex.getMessage(),request.getDescription(false)));
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), "Password Mismatch", HttpStatus.BAD_REQUEST,list);

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceAlreadyExistException.class)
    public final ResponseEntity<Object> resourceAlreadyExist(ResourceAlreadyExistException ex, WebRequest request){
        List<String> list = new ArrayList<String>(Arrays.asList(ex.getMessage(),request.getDescription(false)));
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), "Resource already Exist", HttpStatus.BAD_REQUEST,list);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ResourceDoesNotExistException.class)
    public final ResponseEntity<Object> resourceDoesNotExist(ResourceDoesNotExistException ex, WebRequest request){
        List<String> list = new ArrayList<String>(Arrays.asList(ex.getMessage(),request.getDescription(false)));
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), "Resource does not Exist", HttpStatus.BAD_REQUEST,list);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public final ResponseEntity<Object> invalidTokenException(InvalidTokenException ex, WebRequest request){
        List<String> list = new ArrayList<String>(Arrays.asList(ex.getMessage(),request.getDescription(false)));
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), "Invalid Token", HttpStatus.BAD_REQUEST,list);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        List<String> details = new ArrayList<String>();
        details = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + " : " + error.getDefaultMessage())
                .collect(Collectors.toList());

         ExceptionResponse exceptionResponse= new ExceptionResponse(
                         new Date(),
                 "Validation Errors",
                 HttpStatus.BAD_REQUEST,
                         details);


        return new ResponseEntity<>(exceptionResponse,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(
            Exception ex,
            WebRequest request) {

        List<String> details = new ArrayList<String>();
        details.add(ex.getMessage());

        ExceptionResponse exceptionResponse = new ExceptionResponse(
                        new Date(),
                "Constraint Violations",
                HttpStatus.BAD_REQUEST,
                        details);

        return new ResponseEntity<>(exceptionResponse,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public final ResponseEntity<Object> illegalArgument(InvalidFormatException ex, WebRequest request){
        List<String> list = new ArrayList<String>(Arrays.asList(ex.getMessage(),request.getDescription(false)));
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), "Illegal argument ", HttpStatus.BAD_REQUEST,list);

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
