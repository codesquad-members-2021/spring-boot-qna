package com.codessquad.qna.domain;

import javax.persistence.*;

@Entity
public class Answer extends AbstractEntity {
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_author"))
    private User author;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer"))
    private Question question;

    @Column(length = 2000)
    private String contents;

    protected Answer() {
    }

    public Answer(User author, Question question, String contents) {
        this.author = author;
        this.question = question;
        this.contents = contents;
    }

    public boolean isSameAuthor(User loginUser) {
        return this.author.equals(loginUser);
    }

    public User getAuthor() {
        return author;
    }

    public Question getQuestion() {
        return question;
    }

    public String getContents() {
        return contents;
    }
}

