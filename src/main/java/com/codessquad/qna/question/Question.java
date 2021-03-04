package com.codessquad.qna.question;

import java.time.LocalDateTime;

public class Question {
    private String writer;
    private String title;
    private String contents;
    private LocalDateTime createDateTime = LocalDateTime.now();

    public Question() {
    }

    public Question(String writer, String title, String contents, LocalDateTime createDateTime) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createDateTime = createDateTime;
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

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(LocalDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }
}
