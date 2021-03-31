package com.codessquad.qna.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
public class Answer extends AbstractEntity {

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_user"), nullable = false)
    private User writer;

    @Column(nullable = false)
    private String contents;

    @Column(columnDefinition = "boolean default false")
    private boolean deleted;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"), nullable = false)
    private Question question;

    public void save(User writer, Question question) {
        this.writer = writer;
        this.question = question;
    }

    public void update(Answer answer) {
        this.contents = answer.contents;
    }

    public void delete() {
        this.deleted = true;
    }

    public boolean matchWriter(User loginUser) {
        return this.writer.matchId(loginUser.getId());
    }

    public Long getQuestionId() {
        return this.question.getId();
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

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @Override
    public String toString() {
        return super.toString() + ", " +
                "writer: " + this.writer.getUserId() + ", " +
                "contents: " + this.contents + ", " +
                "question: " + this.question.getId() + ", ";
    }

}
