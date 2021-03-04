package com.codessquad.qna.web;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Question {
    private int index;
    private String writer;
    private String title;
    //spring에서는 contents를 뭘로 받나
    private String contents;
    private LocalDateTime createdDate;

    Question() {
        this.createdDate = LocalDateTime.now();
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

    public void setIndex(int index) {
        this.index = index;
    }

    public void setCreatedDate() {
        this.createdDate = LocalDateTime.now();
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

    public int getIndex() {
        return index;
    }

    public String getFormattedCreatedDate() {
        if (createdDate == null) {
            return "";
        }
        return createdDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
