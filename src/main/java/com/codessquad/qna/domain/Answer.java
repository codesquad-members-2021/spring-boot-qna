package com.codessquad.qna.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Answer extends AbstractEntity {

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
    @JsonBackReference
    private Question question;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_writer"))
    private User writer;

    @Column(nullable = false, length = 2000)
    @NotBlank(message = "내용은 필수 입력 값입니다.")
    private String contents;

    @Column(nullable = false)
    private boolean isDelete;

    public Answer() {
    }

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

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void delete() {
        this.isDelete = true;
    }

    public boolean isMatch(User loginUser) {
        return this.writer.getUserId().equals(loginUser.getUserId());
    }

    public boolean isDeleted() {
        return isDelete;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + getId() +
                ", contents='" + contents + '\'' +
                ", deleted=" + isDelete +
                ", createdDateTime=" + getCreatedDateTime() +
                '}';
    }

}
