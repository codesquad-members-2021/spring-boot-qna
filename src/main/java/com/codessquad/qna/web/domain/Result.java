package com.codessquad.qna.web.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Result {
    public static final int STATUS_OK = 200;

    @JsonProperty
    private final int status;

    @JsonProperty
    private final String message;

    public Result(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
