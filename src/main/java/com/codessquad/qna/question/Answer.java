package com.codessquad.qna.question;

import com.codessquad.qna.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comment;
    private LocalDateTime createDateTime;
    private LocalDateTime updateDateTime;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_question"))
    private Question question;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    private User writer;

    protected Answer() {
    }

    public Answer(String comment, LocalDateTime createDateTime, Question question, User writer) {
        this.comment = comment;
        this.createDateTime = createDateTime == null ? LocalDateTime.now() : createDateTime;
        this.question = question;
        this.writer = writer;
    }

    public Long getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public LocalDateTime getUpdateDateTime() {
        return updateDateTime;
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

    public void verifyWriter(User target) {
        writer.verifyWith(target);
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", comment='" + comment + '\'' +
                ", createDateTime=" + createDateTime +
                ", updateDateTime=" + updateDateTime +
                ", question=" + question.getId() +
                ", writer=" + writer +
                '}';
    }
}
