package com.codessquad.qna.valid;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;

public class QuestionValidator {

    public static void validate(Question question) {
        validTitle(question.getTitle());
        validWriter(question.getWriter());
    }

    private static void validTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("제목이 비었습니다.");
        }
    }

    private static void validWriter(User writer) {
        if (writer == null || writer.getUserId().trim().isEmpty()) {
            throw new IllegalArgumentException("글쓴이가 비었습니다.");
        }
    }
}
