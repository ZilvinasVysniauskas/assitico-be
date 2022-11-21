package com.assistico.planner.exceptions;

public class UserNotFoundByEmailToken extends Exception {
    public UserNotFoundByEmailToken(String error) {
        super(error);
    }
}
