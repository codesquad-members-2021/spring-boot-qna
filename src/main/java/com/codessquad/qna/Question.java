package com.codessquad.qna;

import java.time.LocalDate;

public class Question {

    private String writer;
    private String title;
    private String contents;
    private int id;
    private LocalDate time;

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

    public LocalDate getTime() {
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

    public void setTime(LocalDate time) {
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
