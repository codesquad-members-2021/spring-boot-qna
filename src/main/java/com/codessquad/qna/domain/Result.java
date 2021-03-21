package com.codessquad.qna.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Result {

    @JsonProperty
    private int status;

    @JsonProperty
    private String message;

    private Result(int status, String message) {
        this.status = status;
        this.message = message;
    }


    public static Result ok() {
        return new Result(200, "OK");
    }

    public static Result notFound() {
        return new Result(404, "NOT_FOUND");
    }

    public static Result fobidden() {
        return new Result(403, "FOBIDDEN");
    }

    public static Result found(String url) {
        return new Result(302, url);
    }

    public static Result fail(int status, String message) {
        return new Result(status, message);
    }

}
