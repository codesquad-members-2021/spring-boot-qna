package com.codessquad.qna.domain.answer;

import com.codessquad.qna.domain.IdAndBaseTimeEntity;
import com.codessquad.qna.domain.question.Question;
import com.codessquad.qna.domain.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
public class Answer extends IdAndBaseTimeEntity {

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
    @JsonProperty
    private Question question;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    @JsonProperty
    private User writer;

    @JsonProperty
    private String contents;

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
        return writer.equals(user);
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
