package com.jubydull.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class StudentExistException extends RuntimeException {
    public StudentExistException(String message) {
        super(message);
    }
}
