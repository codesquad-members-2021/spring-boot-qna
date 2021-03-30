package com.codessquad.qna.domain.answer;

import com.codessquad.qna.domain.BaseTimeEntity;
import com.codessquad.qna.domain.question.Question;
import com.codessquad.qna.domain.user.User;
import com.codessquad.qna.exception.NotAuthorizationException;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Answer extends BaseTimeEntity {

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
    private Question question;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    private User writer;

    private String contents;

    @JsonIgnore
    private boolean deleted;

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void delete() {
        deleted = true;
    }

    public boolean isWrittenBy(User user) {
        if (!writer.equals(user)) {
            throw new NotAuthorizationException();
        }
        return true;
    }

    public void update(Answer answer) {
        this.contents = answer.contents;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + getId() +
                ", question=" + question +
                ", writer=" + writer +
                ", contents='" + contents + '\'' +
                ", date='" + getCreateDateTime() + '\'' +
                '}';
    }
}
