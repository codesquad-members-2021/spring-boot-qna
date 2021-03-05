package com.codessquad.qna.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Question {

    @Id
    @GeneratedValue
    private Long id;

    private String writer;
    private String title;
    private String contents;
    private LocalDateTime writeTime;

    public LocalDateTime getWriteTime() {
        return writeTime;
    }

    public void setWriteTime(LocalDateTime writeTime) {
        this.writeTime = writeTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getContents() {
        return contents;
    }

}
