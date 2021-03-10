package com.codessquad.qna.question.dto;

import com.codessquad.qna.question.domain.Question;

public class QuestionRequest {
    private String writer;
    private String title;
    private String contents;

    protected QuestionRequest() {}

    public QuestionRequest(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public static QuestionRequest of(Question question) {
        return new QuestionRequest(
                question.getWriter(),
                question.getTitle(),
                question.getContents());
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public Question toQuestion() {
        return new Question(writer, title, contents);
    }
}
