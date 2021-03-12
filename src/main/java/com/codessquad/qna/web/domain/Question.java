package com.codessquad.qna.web.domain;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Entity
public class Question {
    @Transient
    public static final String DATE_PATTERN = "yyyy-MM-dd HH:mm";
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable=false, length=20)
    private String writer;

    @Column(nullable=false, length=20)
    private String title;

    @Column(nullable=false, length=500)
    private String contents;

    @Column(nullable=false, length=20)
    private String dateTime;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
