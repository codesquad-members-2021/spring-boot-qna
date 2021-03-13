package com.codessquad.qna.dto;

import com.codessquad.qna.entity.User;

import java.time.LocalDateTime;

public class PostDto {

    private final LocalDateTime updateDateTime = LocalDateTime.now();
    private String title;
    private User author;
    private String body;

    public PostDto() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
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
