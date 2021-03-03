package com.codessquad.qna.question;

import java.time.ZonedDateTime;

public class Question {
    private int id;
    private String writer;
    private String title;
    private String contents;
    private ZonedDateTime time;
    private int point;

    public Question(QuestionRequest questionRequest) {
        writer = questionRequest.getWriter();
        title = questionRequest.getTitle();
        contents = questionRequest.getContents();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getId() {
        return id;
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

    public ZonedDateTime getTime() {
        return time;
    }

    public int getPoint() {
        return point;
    }

}
