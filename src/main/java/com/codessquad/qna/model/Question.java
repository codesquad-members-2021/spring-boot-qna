package com.codessquad.qna.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Question {
    private int id;
    private String writer;
    private String title;
    private String contents;
    private LocalDateTime writetime;

    public Question(String writer, String title, String contents) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.writetime = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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


    public String getWritetime() {
        return writetime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public void setWritetime(LocalDateTime writetime) {
        this.writetime = writetime;
    }

    @Override
    public String toString() {
        return "Question{" +
                "writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", writetime=" + writetime +
                '}';
    }
}
