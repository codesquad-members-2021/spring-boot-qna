package com.codessquad.qna.web;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Question {
    private int id;
    private String writer;
    private String title;
    private String contents;
    private String currentTime;

    public Question(Calendar currentTime) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat Format = new SimpleDateFormat("yyyy-MM-dd EEEEE HH:mm:ss");
        this.currentTime = Format.format(cal.getTime());
    }

    public void setId(int id) {
        this.id = id;
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

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public int getId() {
        return id;
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

    public String getCurrentTime() {
        return currentTime;
    }

}
