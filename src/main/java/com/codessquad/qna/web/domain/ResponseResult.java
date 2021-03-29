package com.codessquad.qna.web.domain;

public class ResponseResult {
    private boolean valid;
    private String errorMessage;

    private ResponseResult() {
        this(true, null);
    }

    public ResponseResult(boolean valid, String errorMessage) {
        this.valid = valid;
        this.errorMessage = errorMessage;
    }

    public static ResponseResult ok() {
        return new ResponseResult();
    }

    public static ResponseResult fail(String errorMessage) {
        return new ResponseResult(false, errorMessage);
    }

    public boolean isValid() {
        return valid;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
