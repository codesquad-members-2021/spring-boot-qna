package com.codessquad.qna.domain;

import javax.persistence.*;

@Entity
public class Answer extends BaseEntity {
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_question"))
    private Question question;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    private User writer;

    @Column(nullable = false, length = 3000)
    private String contents;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean deleted;

    protected Answer() {
    }

    public Answer(Question question, User writer, String contents) {
        this.question = question;
        this.writer = writer;
        this.contents = contents;

        this.question.increaseAnswerCount();
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

        this.question.decreaseAnswerCount();
    }

    public boolean isWriter(User user) {
        return writer.isSameId(user);
    }
}
