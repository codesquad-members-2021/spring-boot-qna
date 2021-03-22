package com.codessquad.qna.domain;


import jdk.vm.ci.meta.Local;
import org.joda.time.DateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Question {

    @Id
    @GeneratedValue
    private Long id;

    private String writer;
    private String title;
    private String contents;
    private LocalDateTime writeTime;

    public Question() {  }
    public Question(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.writeTime = LocalDateTime.now();
    }

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

    public String getWriteTime() {
        if(writeTime == null) {
            return "";
        }
        return writeTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public void setWriteTime(LocalDateTime writeTime) {
        this.writeTime = writeTime;
    }



    @Override
    public String toString() {
        return "Question{" +
                "questionId=" + id +
                ", writer='" + writer + '\'' +
                ", subject='" + title + '\'' +
                ", content='" + contents + '\'' +
                '}';
    }
}
