package com.codessquad.qna.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Post {

    private int postId;
    private String title;
    private String author;
    private String body;
    private int comment = 0; // default value
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
    private String date;


    public Post(String title, String author, String body) {
        this.title = title;
        this.author = author;
        this.body = body;
        this.date = simpleDateFormat.format(new Date());
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
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

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }
}
