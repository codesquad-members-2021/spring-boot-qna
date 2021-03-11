package com.codessquad.qna.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity
public class Post {

    @Id
    @Column(name = "POST_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    private String title;
    private String author;
    private String body;
    private LocalDateTime date;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    protected Post() {
    }

    public Post(String title, String author, String body) {
        this.title = title;
        this.author = author;
        this.body = body;
        this.date = LocalDateTime.now();
    }

    public Long getPostId() {
        return postId;
    }

    public String getTitle() {
        return title;
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

    public List<Comment> getComment() {
        return Collections.unmodifiableList(comments);
    }

    public boolean isMatchedAuthor(User user) {
        return this.author.equals(user.getUserId());
    }

    public void change(Post post) {
        this.title = post.title;
        this.body = post.body;
    }

    @Override
    public String toString() {
        return "Post{" +
                "postId=" + postId +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", body='" + body + '\'' +
                ", date='" + date + '\'' +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Post)) return false;
        Post post = (Post) o;
        return Objects.equals(postId, post.postId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postId);
    }

}
