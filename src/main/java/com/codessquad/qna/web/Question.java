package com.codessquad.qna.web;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Question {
    private static final DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private int id;
    private String writer;
    private String title;
    private String contents;
    private LocalDateTime currentTime;

    public Question() {
        currentTime = LocalDateTime.now();
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

    public void setCurrentTime(LocalDateTime currentTime) {
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
        return currentTime.format(pattern);
    }

}
