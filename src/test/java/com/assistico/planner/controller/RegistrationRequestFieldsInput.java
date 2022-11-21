package com.assistico.planner.controller;

public enum RegistrationRequestFieldsInput {
    VALID_EMAIL("test@mail.com"),
    INVALID_EMAIL("test@"),
    VALID_USERNAME("username"),
    TOO_SHORT_USERNAME("us"),
    TOO_LONG_USERNAME("tooooooooooooooooooooooooooooooooooooLong"),
    VALID_PASSWORD("VienasVienas123?"),
    INVALID_PASSWORD_NO_CAPITAL_LETTER("vienasvienas123?"),
    INVALID_PASSWORD_NO_SYMBOL("VienasVienas123"),
    INVALID_PASSWORD_NO_NUMBER("VienasVienas?"),
    INVALID_PASSWORD_TOO_SHORT("Viena1?"),
    INVALID_PASSWORD_TOO_LONG("Vienas1Vienas1Vienas1Vienas1Vienas1?"),
    NOT_MATCHING_CONFIRMATION_PASSWORD("VienasVienas321?");

    private final String input;
    RegistrationRequestFieldsInput(String input) {
        this.input = input;
    }

    public String input() {
        return input;
    }
}
