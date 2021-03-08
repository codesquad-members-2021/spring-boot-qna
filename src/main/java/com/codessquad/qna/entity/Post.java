package com.codessquad.qna.entity;

import com.codessquad.qna.dto.PostDto;
import com.codessquad.qna.util.DateFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.context.annotation.Primary;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@ToString
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    private String title;
    private String author;
    private String body;
    private String date;

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

}
