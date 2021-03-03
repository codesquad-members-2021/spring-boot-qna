package com.codessquad.qna.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Question {
    private long index;
    private String writer;
    private String title;
    private String contents;
    private String createdTime;

    public Question(long index, String writer, String title, String contents) {
        this.index = index;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createdTime = nowDateToString();
    }

    public String nowDateToString() {
        return LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public long getIndex() {
        return index;
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

    public String getCreatedTime() {
        return createdTime;
    }

    public void setIndex(long index) {
        this.index = index;
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

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + index +
                ", writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", createdTime='" + createdTime + '\'' +
                '}';
    }
}
