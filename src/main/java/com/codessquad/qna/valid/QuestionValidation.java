package com.codessquad.qna.valid;

import com.codessquad.qna.domain.Question;

public class QuestionValidation {

    public static void validQuestion(Question question) {
        validTitle(question.getTitle());
        validContents(question.getContents());
    }

    private static void validTitle(String title) {
        if (title.trim().isEmpty()) {
            throw new NullPointerException("제목이 비었습니다.");
        }
    }

    private static void validContents(String contents) {
        if (contents.trim().isEmpty()) {
            throw new NullPointerException("내용이 비었습니다.");
        }
    }

}
