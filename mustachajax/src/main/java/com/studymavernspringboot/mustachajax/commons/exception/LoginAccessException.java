package com.studymavernspringboot.mustachajax.commons.exception;

public class LoginAccessException extends RuntimeException {
    public LoginAccessException(String message) {
        super(message);
    }
}