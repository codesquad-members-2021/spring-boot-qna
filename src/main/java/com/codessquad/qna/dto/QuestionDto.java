package com.codessquad.qna.dto;

import com.codessquad.qna.DateTimeUtils;
import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;

import java.time.LocalDateTime;

public class QuestionDto {
    private User writer;
    private String title;
    private String contents;
    private String createdAt;

    protected QuestionDto() {
    }

    public QuestionDto(String title, String contents) {
        this.title = title;
        this.contents = contents;
        this.createdAt = DateTimeUtils.stringOf(LocalDateTime.now());
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getTime() {
        return createdAt;
    }

    public void setTime(String createdAt) {
        this.createdAt = createdAt;
    }

    public Question toEntity(User user) {
        return new Question(user, this.title, this.contents);
    }
}
