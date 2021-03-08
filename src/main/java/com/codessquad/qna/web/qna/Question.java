package com.codessquad.qna.web.qna;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private long writerId;
    private String writerUserId;
    private String title;
    private String contents;
    private LocalDateTime reportingDate;

    public Question(long writerId, String writerUserId, String title, String contents) {
        this.writerId = writerId;
        this.writerUserId = writerUserId;
        this.title = title;
        this.contents = contents;
        this.reportingDate = LocalDateTime.now();
    }

    public Question() {
        this.reportingDate = LocalDateTime.now();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getWriterUserId() {
        return writerUserId;
    }

    public void setWriterUserId(String writerUserId) {
        this.writerUserId = writerUserId;
    }

    public long getWriterId() {
        return writerId;
    }

    public void setWriterId(long writerId) {
        this.writerId = writerId;
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

    public LocalDateTime getReportingDate() {
        return reportingDate;
    }

    public void setReportingDate(LocalDateTime reportingDate) {
        this.reportingDate = reportingDate;
    }
}
