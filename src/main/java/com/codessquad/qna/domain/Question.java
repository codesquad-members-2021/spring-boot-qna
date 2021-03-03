package com.codessquad.qna.domain;

import java.text.SimpleDateFormat;

public class Question {

    private static int serialCode = 1;
    private int id;
    private String writer;
    private String title;
    private String contents;
    private String time;

    public Question() {
        this.time = formatTime(System.currentTimeMillis());
        this.id = serialCode++;
    }

    public int getId() {
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
        return time;
    }

    private String formatTime(long currentTimeMillis) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(currentTimeMillis);
    }

    @Override
    public String toString() {
        return "Question{" +
            "writer='" + writer + '\'' +
            ", title='" + title + '\'' +
            ", contents='" + contents + '\'' +
            ", time='" + time + '\'' +
            '}';
    }
}
