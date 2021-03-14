package com.codessquad.qna.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Comment {

    @Id
    @Column(name = "COMMENT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "POST_ID")
    private Post post;

    private String author;
    private String body;
    private LocalDateTime date = LocalDateTime.now();

    protected Comment() { }

    public Comment(Post post, String author, String body) {
        this.post = post;
        this.author = author;
        this.body = body;
        this.date = LocalDateTime.now();
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

    public LocalDateTime getDate() {
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
