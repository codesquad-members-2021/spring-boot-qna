package com.codessquad.qna.entity;

import com.codessquad.qna.util.DateFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Comment {

    @Id
    @Column(name = "COMMENT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "POST_ID")
    private Post post;

    private String author;
    private String body;
    private String date;

    public Comment() {
        this.date = DateFormat.ISO8601.format(new Date());
    }

    public Comment(Post post, String author, String body) {
        this.post = post;
        this.author = author;
        this.body = body;
        this.date = DateFormat.ISO8601.format(new Date());
    }

    public Long getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getBody() {
        return body;
    }

    public String getDate() {
        return date;
    }

    public Post getPost() {
        return post;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", post=" + post +
                ", author='" + author + '\'' +
                ", body='" + body + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
