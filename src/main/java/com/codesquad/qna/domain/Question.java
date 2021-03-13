package com.codesquad.qna.domain;

import com.codesquad.qna.util.DateTimeUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Question {
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, length = 32, unique = true)

    private String writer;

    @Column(nullable = false)
    private String title;

    private String contents;
    private LocalDateTime createdDateTime;

    public Question(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        // validate space by String::trim -> title.trim.isEmpty()
        this.contents = contents;
        this.createdDateTime = LocalDateTime.now();
    }

    protected Question() {
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public String getCreatedTime() {
        return DateTimeUtils.formatByPattern(createdDateTime);
    }

}
