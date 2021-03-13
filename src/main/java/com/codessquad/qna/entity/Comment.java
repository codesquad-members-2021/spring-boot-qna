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

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "USER_PK")
    private User author;

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
