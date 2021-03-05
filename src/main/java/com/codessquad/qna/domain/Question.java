package com.codessquad.qna.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Question {
    private String writer;
    private String title;
    private String contents;
    private int id;
    private String timeCreated;

    public Question(String writer, String title, String contents, int id) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.id = id;
        this.timeCreated = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm"));
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(String contents) {
        this.contents = contents;
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

    public int getId() {
        return id;
    }

    public String getTimeCreated() {
        return timeCreated;
    }

    @Override
    public String toString() {
        return "Question{" +
                "writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", id=" + id +
                ", timeCreated='" + timeCreated + '\'' +
                '}';
    }
}
