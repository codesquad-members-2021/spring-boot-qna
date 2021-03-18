package com.codessquad.qna.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Qna {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String writer;

    private String title;
    private String contents;
    private LocalDateTime createdDateTime = LocalDateTime.now();

    public Long getId() {
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

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getFormattedDateTime() {
        return createdDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public boolean checkWriter(String userId) {
        return this.writer.equals(userId);
    }

    public void update(Qna updateQna) {
        this.title = updateQna.title;
        this.contents = updateQna.contents;
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
