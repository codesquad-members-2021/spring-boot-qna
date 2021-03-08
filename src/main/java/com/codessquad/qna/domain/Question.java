package com.codessquad.qna.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String writer;
    private String title;
    private String contents;
    private LocalDateTime time;
    private int point;

    protected Question() {}

    public Question(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public Long getId() {
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

    public LocalDateTime getTime() {
        return time;
    }

    public int getPoint() {
        return point;
    }
}
