package com.codessquad.qna.dto;

import com.codessquad.qna.entity.Post;

import java.util.Date;

public class PostDto {

    private String title;
    private String author;
    private String body;
    private String date;

    public PostDto() {
        this.date = Post.ISO8601.format(new Date());
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
