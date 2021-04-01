package com.codessquad.qna.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;

public class Result {

    @JsonManagedReference
    private boolean valid;

    @JsonManagedReference
    private String errorMessage;

    private Result(boolean valid, String errorMessage) {
        this.valid = valid;
        this.errorMessage = errorMessage;
    }

    public static Result ok() {
        return new Result(true, null);
    }

    public static Result fail(String errorMessage) {
        return new Result(false, errorMessage);
    }
}
