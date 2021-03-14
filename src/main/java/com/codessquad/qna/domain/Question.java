package com.codessquad.qna.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Question {

    @Id
    @GeneratedValue
    private Long id;

    private String userId;

    private String title;

    private String contents;

    public Question() {
    }

    public Question(String userId, String title, String contents) {
        this.userId = userId;
        this.title = title;
        this.contents = contents;
    }

    public Long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }
}
