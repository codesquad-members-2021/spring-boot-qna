package com.codessquad.qna.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Result {
    @JsonProperty
    private boolean valid;

    @JsonProperty
    private String message;

    private Result(boolean valid, String message) {
        this.valid = valid;
        this.message = message;
    }

    public static Result OK() {
        return new Result(true, null);
    }

    public static Result OK(String msg) {
        return new Result(true, msg);
    }

    public static Result Fail(String msg) {
        return new Result(false, msg);
    }
}
