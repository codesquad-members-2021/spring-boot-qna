package com.codessquad.qna.answer.dto;

import com.codessquad.qna.answer.domain.Answer;
import com.codessquad.qna.question.domain.Question;
import com.codessquad.qna.user.domain.User;

import javax.validation.constraints.NotBlank;

public class AnswerRequest {
    @NotBlank(message = "answer contents is blank")
    private String contents;

    protected AnswerRequest() {}

    public AnswerRequest(String contents) {
        this.contents = contents;
    }

    public Answer toAnswer(Question question, User writer) {
        return new Answer(question, writer, contents);
    }
}
