package com.codessquad.qna.answer.domain;

import com.codessquad.qna.common.BaseEntity;
import com.codessquad.qna.question.domain.Question;
import com.codessquad.qna.user.domain.User;

import javax.persistence.*;

@Entity
public class Answer extends BaseEntity {
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
    private Question question;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    private User writer;

    @Lob
    private String contents;

    protected Answer() {}

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
}
