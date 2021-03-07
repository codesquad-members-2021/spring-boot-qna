package com.codessquad.qna.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Question {

    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm";

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 20)
    private String writer;
    private String title;
    private String contents;
    private LocalDateTime time = LocalDateTime.now();

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

    public String getTime() {
        return formatTime(time);
    }

    private String formatTime(LocalDateTime time) {
        return time.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

    @Override
    public String toString() {
        return "Question{" +
            "id=" + id +
            ", writer='" + writer + '\'' +
            ", title='" + title + '\'' +
            ", contents='" + contents + '\'' +
            ", time=" + time +
            '}';
    }
}
