package com.codessquad.qna.domain;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Answer {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
    private Question question;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    private User writer;

    private String comment;
    private LocalDateTime createdDateTime;
    private boolean deleted;

    protected Answer() {
    }

    public Answer(String comment) {
        this.comment = comment;
        this.createdDateTime = LocalDateTime.now();
        this.deleted = false;
    }

    public Long getId() {
        return id;
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

    public boolean isDeleted() {
        return deleted;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
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
