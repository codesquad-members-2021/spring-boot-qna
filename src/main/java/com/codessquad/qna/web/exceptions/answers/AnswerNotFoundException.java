package com.codessquad.qna.web.exceptions.answers;

public class AnswerNotFoundException extends RuntimeException {
    private static final String ERROR_MESSAGE = "존재하지 않는 답변입니다!";

    public AnswerNotFoundException() {
        super("존재하지 않는 답변객체입니다!");
    }
}
