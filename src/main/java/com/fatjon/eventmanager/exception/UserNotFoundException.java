package com.fatjon.eventmanager.exception;

public class UserNotFoundException extends Exception{
    public UserNotFoundException(String user_not_found) {

    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
