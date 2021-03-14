package com.codessquad.qna.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
public class Post {

    @Id
    @Column(name = "POST_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    private String title;

    @ManyToOne
    @JoinColumn(name = "USER_PK")
    private User author;

    private boolean deleteFlag = false;
    private String body;


    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER)
    private final List<Comment> comments = new ArrayList<>();

    private LocalDateTime createDateTime = LocalDateTime.now();

    protected Post() {
    }

    public Post(String title, User author, String body) {
        this.title = title;
        this.author = author;
        this.body = body;
    }

    public Long getPostId() {
        return postId;
    }

    public String getTitle() {
        return title;
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

    public List<Comment> getComment() {
        return Collections.unmodifiableList(comments);
    }

    public void delete() {
        deleteFlag = true;
    }

    public boolean isDeleteFlag() {
        return deleteFlag;
    }

    public boolean isMatchedAuthor(User user) {
        return this.author.isMatchedId(user.getId());
    }

    public void change(Post post) {
        this.title = post.title;
        this.body = post.body;
        this.createDateTime = post.createDateTime;
    }

    @Override
    public String toString() {
        return "Post{" +
                "postId=" + postId +
                ", title='" + title + '\'' +
                ", author='" + author.toString() + '\'' +
                ", body='" + body + '\'' +
                ", createDateTime='" + createDateTime + '\'' +
                '}';
    }

}
