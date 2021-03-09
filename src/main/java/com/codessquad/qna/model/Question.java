package com.codessquad.qna.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Question {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String writer;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private Date date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
