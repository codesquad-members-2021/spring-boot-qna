package com.codessquad.qna.web.domain;

import org.springframework.beans.factory.annotation.Autowired;

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

    @Column(nullable = false, length = 20)
    private String writer;

    @Column(nullable = false, length = 20)
    private String title;

    @Column(nullable = false, length = 500)
    private String contents;

    @Column(nullable = false, length = 20)
    private String dateTime;

    protected Question() {}

    public Question(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
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

    public Long getId() {
        return id;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
