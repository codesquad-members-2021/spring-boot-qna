package com.codessquad.qna.exception;

public class UnauthorizedProfileModificationException extends RuntimeException {
    public static final String PROFILE_MODIFICATION_FAILED = "답변글을 수정/삭제 할 수 있는 권한이 부족하여 실패";

    public UnauthorizedProfileModificationException() {
        super(PROFILE_MODIFICATION_FAILED);
    }
}
