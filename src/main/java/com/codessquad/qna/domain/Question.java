package com.codessquad.qna.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Question {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String writer;

    private String title;

    @Column(nullable = false, length = 2000)
    private String contents;

    private LocalDateTime timeCreated = LocalDateTime.now();

    public Question(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.timeCreated = LocalDateTime.now();
    }

    protected Question() {}

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public void setTimeCreated() {
        this.timeCreated = LocalDateTime.now();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(String contents) {
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

    public long getId() {
        return id;
    }

    public LocalDateTime getTimeCreated() {
        return timeCreated;
    }

    public String getFormattedTimeCreated() {
        return timeCreated.format(DATE_TIME_FORMATTER);
    }

    @Override
    public String toString() {
        return "Question{" +
                "writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", id=" + id +
                ", timeCreated='" + timeCreated + '\'' +
                '}';
    }
}
