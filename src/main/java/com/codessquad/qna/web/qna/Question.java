package com.codessquad.qna.web.qna;

import java.util.Date;

public class Question {
    private int id;
    private String writerId;
    private String title;
    private String contents;
    private Date reportingDate;

    public Question(int id, String writerId, String title, String contents) {
        this.id = id;
        this.writerId = writerId;
        this.title = title;
        this.contents = contents;
        this.reportingDate = new Date();
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

    public Date getReportingDate() {
        return reportingDate;
    }

    public void setReportingDate(Date reportingDate) {
        this.reportingDate = reportingDate;
    }
}
