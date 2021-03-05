package com.codessquad.qna.web.qna;

import java.time.LocalDateTime;

public class Question {
    private int id;
    private String writerId;
    private String title;
    private String contents;
    private LocalDateTime reportingDate;

    public Question(int id, String writerId, String title, String contents) {
        this.id = id;
        this.writerId = writerId;
        this.title = title;
        this.contents = contents;
        this.reportingDate = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWriterId() {
        return writerId;
    }

    public void setWriterId(String writerId) {
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
