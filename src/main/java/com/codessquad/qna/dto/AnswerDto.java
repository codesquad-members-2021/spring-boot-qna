package com.codessquad.qna.dto;

import com.codessquad.qna.DateTimeUtils;
import com.codessquad.qna.domain.Answer;
import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;

import java.time.LocalDateTime;

public class AnswerDto {

    private User writer;
    private String contents;
    private Question question;
    private String time;

    protected AnswerDto() {
    }

    public AnswerDto(String contents) {
        this.contents = contents;
        this.time = DateTimeUtils.stringOf(LocalDateTime.now());
    }

    public User getWriter() {
        return writer;
    }

    public String getContents() {
        return contents;
    }

    public Question getQuestion() {
        return question;
    }

    public String getTime() {
        return time;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Answer toEntity(Question question, User user) {
        return new Answer(user, question, this.contents, this.time);
    }
}
