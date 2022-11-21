package com.assistico.planner.exceptions;

public class ConfirmationEmailNotSentException extends Exception {
    public ConfirmationEmailNotSentException(String errorMessage) {
        super(errorMessage);
    }
}
