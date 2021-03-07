package com.codessquad.qna.model;


import java.text.SimpleDateFormat;
import java.util.Date;

public class Question {
    private String writer;
    private String title;
    private String contents;
    private Date writetime;

    public Question(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.writetime = new Date();
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
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(writetime);
    }

    public void setWritetime(Date writetime) {
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
