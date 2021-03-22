package com.codessquad.qna.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class QuestionDto {
    private String writer;
    private String title;
    private String contents;
    private String time;

    protected QuestionDto(){

    }

    public QuestionDto(String writer, String title, String contents){
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
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
}
