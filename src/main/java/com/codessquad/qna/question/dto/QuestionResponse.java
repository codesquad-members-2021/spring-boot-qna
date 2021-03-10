package com.codessquad.qna.question.dto;

import com.codessquad.qna.question.domain.Question;

public class QuestionResponse {
    private Long id;
    private String writer;
    private String title;
    private String contents;

    protected QuestionResponse() {}

    public QuestionResponse(Long id, String writer, String title, String contents) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public static QuestionResponse of(Question question) {
        return new QuestionResponse(
                question.getId(),
                question.getWriter(),
                question.getTitle(),
                question.getContents());
    }

    public Long getId() {
        return id;
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
}
