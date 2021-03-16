package com.codessquad.qna.domain;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Answer {

    private static final DateTimeFormatter FORMAT_yyyy_MM_dd_HHmm = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Id
    @GeneratedValue
    @JsonProperty
    private Long id;

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

    @JsonProperty
    private LocalDateTime createdDateTime;

    @JsonIgnore
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

    @JsonGetter("time")
    public String getFormattedTime() {
        return createdDateTime.format(FORMAT_yyyy_MM_dd_HHmm);
    }

    @JsonGetter("questionId")
    public Long getTheQuestionId() {
        return question.getId();
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
