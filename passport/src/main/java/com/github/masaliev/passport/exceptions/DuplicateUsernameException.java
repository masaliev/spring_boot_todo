package com.github.masaliev.passport.exceptions;

public class DuplicateUsernameException extends RuntimeException{

    public DuplicateUsernameException() {
        super("User exists!");
    }
}
