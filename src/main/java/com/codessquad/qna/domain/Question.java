package com.codessquad.qna.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Question {

    private static int serialCode = 1;
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm";
    private int id;
    private String writer;
    private String title;
    private String contents;
    private LocalDate date;

    public Question() {
        this.date = LocalDate.now();
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

    public String getDate() {
        return formatTime(date);
    }

    private String formatTime(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

    @Override
    public String toString() {
        return "Question{" +
            "id=" + id +
            ", writer='" + writer + '\'' +
            ", title='" + title + '\'' +
            ", contents='" + contents + '\'' +
            ", date=" + date +
            '}';
    }
}
