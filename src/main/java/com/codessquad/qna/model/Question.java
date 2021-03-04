package com.codessquad.qna.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Question {

    private int id;
    private String writer;
    private String title;
    private String contents;
    private String date;

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

    public String getDate() {
        return date;
    }

    public void setDate() {
        SimpleDateFormat format = new SimpleDateFormat ( "yyyy-MM-dd HH:mm");
        this.date = format.format(new Date());;
    }

    @Override
    public String toString() {
        return "writer: " + this.writer + ", " +
                "title: " + this.title + ", " +
                "contents: " + this.contents;
    }
}
