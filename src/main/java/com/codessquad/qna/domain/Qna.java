package com.codessquad.qna.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Qna {
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String writer;

    private String title;

    @Column(nullable = false)
    private String contents;

    private LocalDateTime createdDateTime = LocalDateTime.now();

    public long getId() {
        return id;
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

    public LocalDateTime getDate() {
        return createdDateTime;
    }

    public void setDate(LocalDateTime date) {
        this.createdDateTime = date;
    }

    public String getFormattedDateTime() {
        return createdDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    public String toString() {
        return "Qna{" +
                "writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }
}
