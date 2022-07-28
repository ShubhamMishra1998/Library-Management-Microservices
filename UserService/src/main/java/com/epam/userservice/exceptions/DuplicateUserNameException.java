package com.epam.userservice.exceptions;

public class DuplicateUserNameException extends RuntimeException{

    public DuplicateUserNameException(String message) {
        super(message);
    }

}
