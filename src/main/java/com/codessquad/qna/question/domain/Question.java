package com.codessquad.qna.question.domain;

import com.codessquad.qna.common.BaseTimeEntity;

import javax.persistence.*;

@Entity
public class Question extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String writer;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    protected Question() {}

    public Question(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public Long getId() {
        return id;
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
}
