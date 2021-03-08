package com.codessquad.qna.domain;

import java.time.LocalDateTime;

public class Question {

    private int id;
    private String writer;
    private String title;
    private String contents;
    private LocalDateTime time;

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

    public LocalDateTime getTime() {
        return time;
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
    public void setId(int id) {
        this.id = id;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Question{" +
                "writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", id=" + id +
                ", time=" + time +
                '}';
    }
}
