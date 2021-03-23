package com.codessquad.qna.dto;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class QuestionDto {
    private User writer;
    private String title;
    private String contents;
    private String time;

    protected QuestionDto(){

    }

    public QuestionDto(String title, String contents){
        this.title = title;
        this.contents = contents;
        this.time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
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
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Question toEntity(User user){
        return new Question(user, this.title, this.contents);
    }
}
