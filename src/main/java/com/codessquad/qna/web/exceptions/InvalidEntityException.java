package com.codessquad.qna.web.exceptions;

public class InvalidEntityException extends RuntimeException {
    public static final String EMPTY_FIELD_IN_USER_ENTITY = "비어있는 항목이 있어서 회원가입에 실패했습니다";
    public static final String EMPTY_FIELD_IN_QUESTION_ENTITY = "비어있는 항목이 있어서 질문 작성에 실패했습니다";
    public static final String EMPTY_FIELD_IN_ANSWER_ENTITY = "비어있는 항목이 있어서 답변 작성에 실패했습니다";

    public InvalidEntityException(String message) {
        super(message);
    }
}
