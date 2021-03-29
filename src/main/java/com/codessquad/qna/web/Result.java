package com.codessquad.qna.web;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Result {
    @JsonProperty
    private boolean valid;
    @JsonProperty
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
