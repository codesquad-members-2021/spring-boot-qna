package com.codessquad.qna.question.domain;

import com.codessquad.qna.common.BaseTimeEntity;
import com.codessquad.qna.user.domain.User;

import javax.persistence.*;

@Entity
public class Question extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;


    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    protected Question() {}

    public Question(User writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public Long getId() {
        return id;
    }

    public User getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }
}
