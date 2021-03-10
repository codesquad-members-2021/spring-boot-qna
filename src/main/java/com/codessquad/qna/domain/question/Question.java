package com.codessquad.qna.domain.question;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Question {
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long index;

    private String writer;
    private String title;
    private String contents;
    private String createdTime;

    protected Question() {
    }

    public Question(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createdTime = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

    public long getIndex() {
        return index;
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

    public String getCreatedTime() {
        return createdTime;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + index +
                ", writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", createdTime='" + createdTime + '\'' +
                '}';
    }
}
