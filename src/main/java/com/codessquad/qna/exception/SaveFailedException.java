package com.codessquad.qna.exception;

public class SaveFailedException extends RuntimeException {
    public SaveFailedException(Class<? extends Object> failedClass) {
        super(failedClass.getName() + " 변경사항 저장에 실패했습니다.");
    }
}
