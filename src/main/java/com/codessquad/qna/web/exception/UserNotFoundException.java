package com.codessquad.qna.web.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String userId){
        super("There is no user id, " + userId);
    }
}
