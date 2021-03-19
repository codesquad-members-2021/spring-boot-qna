package com.codessquad.qna.domain;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Answer extends AbstractEntity {

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
    @JsonIgnore
    private Question question;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    @JsonProperty
    private User writer;

    @JsonProperty
    private String comment;

    @JsonIgnore
    private boolean deleted;

    protected Answer() {
    }

    public Answer(String comment) {
        this.comment = comment;
        this.deleted = false;
    }

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

    public String getComment() {
        return comment;
    }

    @JsonGetter("questionId")
    public Long getTheQuestionId() {
        return question.getId();
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void updateAnswer(Answer updatingAnswer) {
        this.comment = updatingAnswer.comment;
    }

    public boolean matchesWriter(User user) {
        return writer.equals(user);
    }

    public void delete() {
        deleted = true;
    }
}
