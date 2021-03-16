package com.codessquad.qna.entity;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "writer_user_id")
    private User author;

    @ColumnDefault("false")
    private boolean deleted = false;

    private String body;
    private LocalDateTime createDateTime = LocalDateTime.now();

    protected Comment() {
    }

    public Comment(Post post, User author, String body) {
        this.post = post;
        this.author = author;
        this.body = body;
        this.createDateTime = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public User getAuthor() {
        return author;
    }

    public String getBody() {
        return body;
    }

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public Post getPost() {
        return post;
    }

    public void delete() {
        deleted = true;
    }

    public boolean isDeleted() {
        return deleted;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", post=" + post +
                ", author='" + author + '\'' +
                ", body='" + body + '\'' +
                ", createDateTime='" + createDateTime + '\'' +
                '}';
    }

    public boolean isMatchedUser(User user) {
        return author.isMatchedId(user.getId());
    }

    public void update(String body, LocalDateTime updateDateTime) {
        this.body = body;
        this.createDateTime = updateDateTime;
    }

}
