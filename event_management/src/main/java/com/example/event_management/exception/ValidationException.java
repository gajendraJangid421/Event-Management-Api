package com.example.event_management.exception;
import org.springframework.web.bind.annotation.ResponseStatus;
import static org.springframework.http.HttpStatus.LENGTH_REQUIRED;

@ResponseStatus(code = LENGTH_REQUIRED)
public class ValidationException extends RuntimeException{
    public ValidationException(String message){
        super(message);
    }
}
