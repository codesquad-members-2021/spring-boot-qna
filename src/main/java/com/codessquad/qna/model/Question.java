package com.codessquad.qna.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Question {

    private int id;
    private String writer;
    private String title;
    private String contents;
    private Date date;

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
        SimpleDateFormat simpleDate = new SimpleDateFormat( "yyyy-MM-dd HH:mm");
        return simpleDate.format(this.date);
    }

    public void setDate() {
        this.date = new Date();
    }

    @Override
    public String toString() {
        return "writer: " + this.writer + ", " +
                "title: " + this.title + ", " +
                "contents: " + this.contents;
    }
}
