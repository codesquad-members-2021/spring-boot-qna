package com.codessquad.qna.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Answer {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
    private Question question;

    @OneToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    private User writer;

    private String comment;
    private LocalDateTime time;

    protected Answer() {}

    public Answer(String comment) {
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public Question getQuestion() {
        return question;
    }

    public User getWriter() {
        return writer;
    }

    public String getComment() {
        return comment;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public void updateComment(Answer updatedAnswer) {
        this.comment = updatedAnswer.comment;
    }
}
