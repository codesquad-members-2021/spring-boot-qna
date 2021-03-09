package com.codessquad.qna.valid;

import com.codessquad.qna.domain.Question;

public class QuestionValidation {

    public static void validQuestion(Question question) {
        validTitle(question.getTitle());
        validTitle(question.getWriter());
    }

    private static void validTitle(String title) {
        if (title.trim().isEmpty()) {
            throw new NullPointerException("제목이 비었습니다.");
        }
    }

    private static void validWriter(String writer) {
        if (writer.trim().isEmpty()) {
            throw new NullPointerException("글쓴이가 비었습니다.");
        }
    }
}
