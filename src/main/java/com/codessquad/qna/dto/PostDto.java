package com.codessquad.qna.dto;

import java.time.LocalDateTime;

public class PostDto {

    private final LocalDateTime updateDateTime = LocalDateTime.now();
    private String title;
    private String author;
    private String body;

    public PostDto() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDateTime getUpdateDateTime() {
        return updateDateTime;
    }

}
