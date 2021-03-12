package com.codessquad.qna.valid;

import com.codessquad.qna.domain.Question;

public class QuestionValidation {

    public static void validQuestion(Question question) {
        validInfo("title", question.getTitle());
        validInfo("contents", question.getContents());
    }

    private static void validInfo(String entity, String info) {
        if (info.trim().isEmpty()) {
            String error = String.format("%s is empty", entity);
            throw new NullPointerException(error);
        }
    }

}
