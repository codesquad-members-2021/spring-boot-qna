package com.codessquad.qna.question.dto;

import com.codessquad.qna.question.domain.Question;
import com.codessquad.qna.user.domain.User;

import javax.validation.constraints.NotBlank;

public class QuestionRequest {
    @NotBlank(message = "title is blank")
    private String title;

    @NotBlank(message = "contents is blank")
    private String contents;

    protected QuestionRequest() {}

    public QuestionRequest(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public static QuestionRequest of(Question question) {
        return new QuestionRequest(question.getTitle(), question.getContents());
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public Question toQuestion(User writer) {
        return new Question(writer, title, contents);
    }
}
