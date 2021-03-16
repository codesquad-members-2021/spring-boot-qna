package com.codessquad.qna.question.dto;

import com.codessquad.qna.question.domain.Question;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class QuestionResponse {
    private Long id;
    private String writer;
    private String title;
    private String contents;
    private String createdDate;
    private String modifiedDate;

    protected QuestionResponse() {}

    public QuestionResponse(Long id, String writer, String title, String contents, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.contents = contents;

        // FIXME: dto 에서의 로직을 삭제해야한다; 프론트에서 자바스크립트로 format 할수 있도록 한다.
        this.createdDate = format(createdDate);
        this.modifiedDate = format(modifiedDate);
    }

    public static QuestionResponse of(Question question) {
        return new QuestionResponse(
                question.getId(),
                question.getWriter().getUserId(),
                question.getTitle(),
                question.getContents(),
                question.getCreatedDate(),
                question.getModifiedDate());
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

    public String getCreatedDate() {
        return createdDate;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    private String format(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm"));
    }
}
