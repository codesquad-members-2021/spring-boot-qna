package com.codessquad.qna.web.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Question {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private int index;
    private String writer;
    private String title;
    private String contents;
    private LocalDateTime createdDate = LocalDateTime.now();

    public void setIndex(int index) {
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

    public void setCreatedDate() {
        this.createdDate = LocalDateTime.now();
    }

    public int getIndex() {
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

    public String getFormattedCreatedDate() {
        if (createdDate == null) {
            return "";
        }
        return createdDate.format(DATE_TIME_FORMATTER);
    }
}
