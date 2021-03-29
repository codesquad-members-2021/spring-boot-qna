package com.codessquad.qna.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Answer extends AbstractEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    private User writer;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
    @JsonManagedReference
    private Question question;

    @Lob
    private String contents;

    @Column(columnDefinition = "boolean default false")
    private boolean deleted;

    public Answer() {
    }

    public Answer(Question question, String contents, User loginUser) {
        this.question = question;
        this.writer = loginUser;
        this.contents = contents;
        deleted = false;
    }

    public Long getAnswerId() {
        return answerId;
    }

    public User getWriter() {
        return writer;
    }

    public Question getQuestion() {
        return question;
    }

    public String getContents() {
        return contents;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void deleted() {
        deleted = true;
    }


    public boolean isEqualWriter(User sessionUser) {
        return writer.equals(sessionUser);
    }

    public void updateQuestionInfo(Answer newAnswerInfo) {
        this.contents = newAnswerInfo.getContents();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return answerId.equals(answer.answerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(answerId);
    }

    @Override
    public String toString() {
        return "Answer{" +
                "answerId=" + answerId +
                ", writer=" + writer +
                ", question=" + question +
                ", contents='" + contents + '\'' +
                ", deleted=" + deleted +
                '}';
    }
}
