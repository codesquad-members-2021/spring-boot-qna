package com.codessquad.qna.web.domain;

import javax.persistence.*;

@Entity
public class Answer extends AbstractEntity {
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    private User writer;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
    private Question question;

    @Column(length = 1000)
    private String contents;

    protected Answer() {
    }

    public Answer(User writer, Question question, String contents) {
        this.writer = writer;
        this.question = question;
        this.contents = contents;
    }

    public User getWriter() {
        return this.writer;
    }

    public String getContents() {
        return this.contents;
    }

    public boolean isSameWriter(User writer) {
        return this.writer.equals(writer);
    }

    public Question getQuestion() {
        return this.question;
    }
}
