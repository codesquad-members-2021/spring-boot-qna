package com.codessquad.qna.valid;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;

public class QuestionValidator {

    public static void validQuestion(Question question) {
        validTitle(question.getTitle());
        validWriter(question.getWriter());
    }

    private static void validTitle(String title) {
        if (title.trim().isEmpty()) {
            throw new NullPointerException("제목이 비었습니다.");
        }
    }

    private static void validWriter(User writer) {
        if (writer.getUserId().trim().isEmpty()) {
            throw new NullPointerException("글쓴이가 비었습니다.");
        }
    }
}
