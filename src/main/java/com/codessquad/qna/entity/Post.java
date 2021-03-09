package com.codessquad.qna.entity;

import com.codessquad.qna.dto.PostDto;
import com.codessquad.qna.util.DateFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.context.annotation.Primary;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
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
    private String date;

    @OneToMany(mappedBy = "post")
    private List<Comment> comment;

    public Post() {
    }

    public Post(String title, String author, String body) {
        this.title = title;
        this.author = author;
        this.body = body;
        this.date = DateFormat.ISO8601.format(new Date());
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

    public String getDate() {
        return date;
    }

    public List<Comment> getComment() {
        return Collections.unmodifiableList(comment);
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

}
