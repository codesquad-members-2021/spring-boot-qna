package com.codessquad.qna.web.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(Long id){
        super("No user with id number " + id);
    }
}
