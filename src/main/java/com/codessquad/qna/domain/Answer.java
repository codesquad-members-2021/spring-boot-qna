package com.codessquad.qna.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "ANSWER")
public class Answer extends CommonEntity {
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_question"))
    @JsonProperty
    private Question question;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    @JsonProperty
    private User writer;

    @Column(nullable = false, length = 3000)
    @JsonProperty
    private String contents;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean deleted;

    protected Answer() {
    }

    public Answer(Question question, User writer, String contents) {
        this.question = question;
        this.writer = writer;
        this.contents = contents;
    }

    public Question getQuestion() {
        return question;
    }

    public User getWriter() {
        return writer;
    }

    public String getContents() {
        return contents;
    }

    public boolean isDeleted() {
        return this.deleted;
    }

    public void delete() {
        this.deleted = true;
    }

    public boolean isWriter(User user) {
        return writer.isSameId(user);
    }
}
