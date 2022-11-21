package com.assistico.planner.exceptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.assistico.planner.utils.conditions.IsProdEnvironment;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Conditional;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
//todo change implementation to check for environment, currently class just returns false
@Conditional(IsProdEnvironment.class)
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    //todo create two environments for handling exceptions
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        return createResponseEntityWithMessage(ex, "server error");
    }

    @ExceptionHandler(UserNotFoundByEmailToken.class)
    public final ResponseEntity<Object> handleUserNotFoundByToken(Exception ex, WebRequest request) {
        return createResponseEntityWithMessage(ex, "user not found by email token");
    }

    @ExceptionHandler(ConfirmationEmailNotSentException.class)
    public final ResponseEntity<Object> handleConfirmationEmailNotSent(Exception ex, WebRequest request) {
        return createResponseEntityWithMessage(ex, "confirmation email not sent");
    }

    private ResponseEntity<Object> createResponseEntityWithMessage(Exception ex, String message) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse(message, details);
        return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ex.printStackTrace();
        List<String> details = new ArrayList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            FieldError fieldError = (FieldError) error;
            details.add("Field: " + fieldError.getField() + ". Message: " + fieldError.getDefaultMessage());
        }
        ErrorResponse error = new ErrorResponse("Validation Failed", details);
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }
}